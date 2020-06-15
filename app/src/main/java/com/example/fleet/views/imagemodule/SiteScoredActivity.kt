package com.e.dummyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.utils.BaseActivity
import com.example.fleet.databinding.ActivitySiteScoredBinding
import com.example.fleet.sharedpreference.SharedPrefClass


class SiteScoredActivity : BaseActivity() {
    private lateinit var fragmentHomeBinding : ActivitySiteScoredBinding
    private var sharedPrefClass : SharedPrefClass? = null

    override fun initViews() {
        fragmentHomeBinding = viewDataBinding as ActivitySiteScoredBinding
        sharedPrefClass = SharedPrefClass()
        val address = sharedPrefClass!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.POC_ADDRESS
        ).toString()

        //fragmentHomeBinding.txtAddress.setText(address)
    }

    override fun getLayoutId() : Int {
        return R.layout.activity_site_scored
    }
}