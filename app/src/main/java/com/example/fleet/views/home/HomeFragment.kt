package com.example.fleet.views.home

import android.app.Dialog
import android.content.Intent
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.common.UtilsFunctions.showToastError
import com.example.fleet.common.UtilsFunctions.showToastSuccess
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.FragmentHomeBinding
import com.example.fleet.maps.FusedLocationClass
import com.example.fleet.model.CommonModel
import com.example.fleet.model.home.JobsResponse
import com.example.fleet.model.home.QuestionData
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.socket.SocketClass
import com.example.fleet.socket.TrackingActivity
import com.example.fleet.utils.BaseFragment
import com.example.fleet.utils.DialogClass
import com.example.fleet.viewmodels.home.HomeViewModel
import com.example.fleet.views.profile.ProfileActivity
import com.example.fleet.views.settings.MyAccountsActivity
import com.google.android.gms.location.*
import com.google.gson.JsonObject
import com.uniongoods.adapters.QuestionsListAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment() {
    private var mFusedLocationClass : FusedLocationClass? = null
    private var socket = SocketClass.socket
    private var questionList = ArrayList<QuestionData.Data>()
    private var totalQuestionList = ArrayList<QuestionData.Data>()
    private lateinit var fragmentHomeBinding : FragmentHomeBinding
    private lateinit var homeViewModel : HomeViewModel
    private val mJsonObject = JsonObject()
    private val mJobListObject = JsonObject()
    val PERMISSION_ID = 42
    lateinit var mFusedLocationClient : FusedLocationProviderClient
    var currentLat = ""
    var currentLong = ""
    var page = 1
    var totalPage = 0
    var mJsonObjectStartJob = JsonObject()
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    var questionsListAdapter : QuestionsListAdapter? = null

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

        totalQuestionList.clear()
        var data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"
        data.id = "0"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.id = "1"
        data.question = "ffff h fdhrhh j"

        //data.selected = "no"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"

        // data.selected = "yes"
        data.id = "2"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"

        // data.selected = "yes"
        data.id = "3"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"

        // data.selected = "no"
        data.id = "4"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"

        // data.selected = "yes"
        data.id = "5"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"

        // data.selected = "yes"
        data.id = "6"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"

        //data.selected = "no"
        data.id = "7"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"

        // data.selected = "yes"
        data.id = "8"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "ffff h fdhrhh j"

        // data.selected = "no"
        data.id = "9"
        totalQuestionList.add(data)

        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        data.id = "10"
        //data.selected = "no"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        // data.selected = "yes"
        data.id = "11"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        // data.selected = "yes"
        data.id = "12"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        // data.selected = "no"
        data.id = "13"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        // data.selected = "yes"
        data.id = "14"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        // data.selected = "yes"
        data.id = "15"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        //data.selected = "no"
        data.id = "16"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        // data.selected = "yes"
        data.id = "17"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        // data.selected = "no"
        data.id = "18"
        totalQuestionList.add(data)
        data = QuestionData.Data()
        data.question = "bfghngf gfhdfgh ghfjgfh ghjk ghj gh kghj mhj"
        // data.selected = "no"
        data.id = "19"
        totalQuestionList.add(data)
        page = 1
        fragmentHomeBinding.tvQuestionCount.setText("10/20")
        totalPage = totalQuestionList.count() / 10
        for (item in 0 until 10) {
            questionList.add(totalQuestionList[item])
        }
        initRecyclerView()

/*rlNext*/
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
                            // jobsList.clear()
                            homeViewModel.getMyJobs(mJobListObject)

                            UtilsFunctions.showToastSuccess(message!!)
                        }
                        else -> message?.let { UtilsFunctions.showToastError(it) }
                    }

                }
            })

        homeViewModel!!.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {
                when (it) {


                    "rlNext" -> {
                        var allSelected = true
                        for (item in questionList) {
                            if (TextUtils.isEmpty(item.selected)) {
                                allSelected = false
                            }
                        }

                        if (!allSelected) {
                            showToastError("Please select all questions")
                        } else {
                            // page = 2
                            // 2.5

                            if (totalQuestionList.count() > (page * 10)) {

                                questionList.clear()
                                for (item in page * 10 + 1 until totalQuestionList.count()) {
                                    questionList.add(totalQuestionList[item])
                                }
                                // page++

                                questionsListAdapter?.notifyDataSetChanged()
                                //
                                fragmentHomeBinding.scrolView.pageScroll(View.FOCUS_UP)
                                fragmentHomeBinding.rvQuesions.smoothScrollToPosition(0)
                                fragmentHomeBinding.tvQuestionCount.setText("20/20")

                            }

                        }
                    }

                }
            })
        )


    }

    private fun initRecyclerView() {
        questionsListAdapter = QuestionsListAdapter(this@HomeFragment, questionList)
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentHomeBinding.rvQuesions.layoutManager = linearLayoutManager
        fragmentHomeBinding.rvQuesions.adapter = questionsListAdapter
        fragmentHomeBinding.rvQuesions.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    fun radioClick(clickedItem : String, id : String) {
        for (item in 0 until questionList.count()) {
            if (questionList[item].id.equals(id)) {
                questionList[item].selected = clickedItem
            }
        }
        questionsListAdapter?.notifyDataSetChanged()
    }

}