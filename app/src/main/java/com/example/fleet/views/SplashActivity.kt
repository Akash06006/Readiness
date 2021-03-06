package com.example.fleet.views

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.e.dummyproject.ImageListActivity
import com.e.dummyproject.SitePhotosActivity
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.ActivitySplashBinding
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.views.authentication.LoginActivity
import java.util.*

class SplashActivity : BaseActivity() {
    private var mActivitySplashBinding : ActivitySplashBinding? = null
    private var sharedPrefClass : SharedPrefClass? = null
    private var mContext : Context? = null

    override fun getLayoutId() : Int {
        return R.layout.activity_splash
    }

    override fun initViews() {
        mContext = this
        mActivitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        sharedPrefClass = SharedPrefClass()
        val token : String? = "sd"

        if (token != null) {
            sharedPrefClass!!.putObject(
                applicationContext,
                GlobalConstants.NOTIFICATION_TOKEN,
                token
            )
        }

        Timer().schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    checkScreenType()
                }
            }
        }, 1500)
    }

    private fun checkScreenType() {
        var login = ""
        if (checkObjectNull(
                SharedPrefClass().getPrefValue(
                    MyApplication.instance,
                    "isLogin"
                )
            )
        )
            login = sharedPrefClass!!.getPrefValue(this, "isLogin").toString()
        val intent = if (login == "true") {

            Intent(this, SiteInfoActivity::class.java)
            //Intent(this, SiteScoredActivity::class.java)
            //Intent(this, SitePhotosActivity::class.java)

        } else {
            Intent(this, LoginActivity::class.java)
            //Intent(this, DashboardActivity::class.java)
        }

        startActivity(intent)
        finish()
    }

}
