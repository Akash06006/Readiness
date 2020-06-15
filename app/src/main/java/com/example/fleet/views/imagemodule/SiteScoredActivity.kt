package com.e.dummyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.utils.BaseActivity
import com.example.fleet.databinding.ActivitySiteScoredBinding
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.views.authentication.LoginActivity


class SiteScoredActivity : BaseActivity() {
    private lateinit var fragmentHomeBinding : ActivitySiteScoredBinding
    private var sharedPrefClass : SharedPrefClass? = null

    override fun initViews() {
        fragmentHomeBinding = viewDataBinding as ActivitySiteScoredBinding
        sharedPrefClass = SharedPrefClass()

        fragmentHomeBinding.btnSubmt.setOnClickListener({
            startActivity(Intent(this, SitePhotosActivity::class.java))
        } )

    }

    override fun getLayoutId() : Int {
        return R.layout.activity_site_scored
    }
}