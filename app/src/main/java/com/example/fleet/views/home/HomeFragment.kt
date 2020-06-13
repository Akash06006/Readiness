package com.example.fleet.views.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.common.UtilsFunctions.showToastError
import com.example.fleet.common.UtilsFunctions.showToastSuccess
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.database.UploadDataToServer
import com.example.fleet.databinding.FragmentHomeBinding
import com.example.fleet.maps.FusedLocationClass
import com.example.fleet.model.CommonModel
import com.example.fleet.model.home.JobsResponse
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.socket.SocketClass
import com.example.fleet.socket.SocketInterface
import com.example.fleet.socket.TrackingActivity
import com.example.fleet.utils.BaseFragment
import com.example.fleet.utils.DialogClass
import com.example.fleet.utils.DialogssInterface
import com.example.fleet.viewmodels.home.HomeViewModel
import com.google.android.gms.location.*
import com.google.gson.JsonObject
import com.uniongoods.adapters.QuestionsListAdapter
import org.json.JSONObject

class HomeFragment : BaseFragment() {
    private var mFusedLocationClass : FusedLocationClass? = null
    private var socket = SocketClass.socket
    private var jobsList = ArrayList<JobsResponse.Data>()
    private lateinit var fragmentHomeBinding : FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel
    private val mJsonObject = JsonObject()
    private val mJobListObject = JsonObject()
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient : FusedLocationProviderClient
    var currentLat = ""
    var currentLong = ""
    var mJsonObjectStartJob = JsonObject()
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()

    override fun getLayoutResId() : Int {
        return R.layout.fragment_home
    }

    override fun onResume() {
        super.onResume()

    }

    override fun initView() {
        fragmentHomeBinding = viewDataBinding as FragmentHomeBinding
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        fragmentHomeBinding.homeViewModel = homeViewModel

        mFusedLocationClass = FusedLocationClass(activity)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        jobsList.clear()
        initRecyclerView()


        homeViewModel.getJobs().observe(this,
            Observer<JobsResponse> { response->
                baseActivity.stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            if (response.data != null && response.data?.size!! > 0) {
                               /* jobsList.addAll(response.data!!)
                                fragmentHomeBinding.rvJobs.visibility = View.VISIBLE
                                fragmentHomeBinding.tvNoRecord.visibility = View.GONE
                                initRecyclerView()*/
                            }

                        }
                        else -> message?.let {
                            showToastError(it)
                            /*fragmentHomeBinding.rvJobs.visibility = View.GONE*/
                            fragmentHomeBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                    }
                }
            })

        homeViewModel.startCompleteJob().observe(this,
            Observer<CommonModel> { response->
                baseActivity.stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            message?.let { showToastSuccess(message!!) }
                            var status = mJsonObjectStartJob.get("status").toString()
                            if (status.equals("1")) {
                                SharedPrefClass().putObject(
                                    MyApplication.instance.applicationContext,
                                    GlobalConstants.JOB_STARTED,
                                    "true"
                                )
                                GlobalConstants.JOB_STARTED = "true"
                                SharedPrefClass().putObject(
                                    MyApplication.instance.applicationContext,
                                    GlobalConstants.JOBID,
                                    mJsonObjectStartJob.get("jobId")
                                )
                                val intent = Intent(activity, TrackingActivity::class.java)
                                intent.putExtra("data", mJsonObjectStartJob.toString())
                                activity!!.startActivity(intent)
                                activity!!.finish()
                            }

                        }
                        else -> message?.let { showToastError(it) }
                    }

                }
            })

        homeViewModel.acceptReject().observe(this,
            Observer<CommonModel> { response->
                baseActivity.stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            jobsList.clear()
                            homeViewModel.getMyJobs(mJobListObject)

                            UtilsFunctions.showToastSuccess(message!!)
                        }
                        else -> message?.let { UtilsFunctions.showToastError(it) }
                    }

                }
            })
    }

    private fun initRecyclerView() {
        val myJobsListAdapter = QuestionsListAdapter(this@HomeFragment, null)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentHomeBinding.rvQuesions.layoutManager = linearLayoutManager
        fragmentHomeBinding.rvQuesions.adapter = myJobsListAdapter
        fragmentHomeBinding.rvQuesions.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

}