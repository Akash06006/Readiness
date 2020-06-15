package com.example.fleet.views.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.ActivityDashboardBinding
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.views.SiteInfoActivity

class DashboardActivity : BaseActivity() {
    var activityDashboardBinding : ActivityDashboardBinding? = null
    private var confirmationDialog : Dialog? = null
    private var ratingDialog : Dialog? = null
    var fragmentManager : FragmentManager? = null

    var fragment : Fragment? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initViews() {
        activityDashboardBinding = viewDataBinding as ActivityDashboardBinding
        fragmentManager = supportFragmentManager
        // replaceFragmetn(HomeFragment())
        showSurveySuccessDialog()
    }

    override fun getLayoutId() : Int {
        return R.layout.activity_dashboard
    }

    private fun showSurveySuccessDialog() {
        /*val siteName = SharedPrefClass()!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.POC_ADDRESS
        ).toString()*/
        val siteName = "Akash"
        confirmationDialog = Dialog(this, R.style.transparent_dialog)
        confirmationDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(this),
                R.layout.survey_submitted_success_dialog,
                null,
                false
            )

        confirmationDialog?.setContentView(binding.root)
        confirmationDialog?.setCancelable(false)

        confirmationDialog?.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        confirmationDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val cancel = confirmationDialog?.findViewById<Button>(R.id.btnDone)
        val tvMessage = confirmationDialog?.findViewById<TextView>(R.id.tvMessage)
        tvMessage?.setText(tvMessage?.text.toString() + " " + siteName)
        cancel?.setOnClickListener {

            val intent = Intent(this, SiteInfoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        confirmationDialog?.show()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setHeadings() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_layout)
        when (fragment) {
            is HomeFragment -> {
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onResume() {
        super.onResume()
        setHeadings()
    }


    fun replaceFragmetn(fragment : Fragment) {

        val fragmentTransaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment, "h")
        fragmentTransaction.addToBackStack("h")
        fragmentTransaction.commit()
    }


    override fun onBackPressed() {

    }
}