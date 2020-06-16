package com.example.fleet.views.authentication

import android.content.Intent
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.ActivityLoginBinding
import com.example.fleet.model.LoginResponse
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.viewmodels.LoginViewModel
import com.example.fleet.views.SiteInfoActivity
import com.google.gson.JsonObject

class LoginActivity : BaseActivity() {
    private lateinit var activityLoginbinding : ActivityLoginBinding
    private lateinit var loginViewModel : LoginViewModel
    override fun getLayoutId() : Int {
        return R.layout.activity_login
    }

    private val mJsonObject = JsonObject()

    override fun initViews() {
        activityLoginbinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        activityLoginbinding.loginViewModel = loginViewModel
        checkAndRequestPermissions()
        loginViewModel.loginResponse().observe(this,
            Observer<LoginResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            showToastSuccess("you are logged in successfully")
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.USERID,
                                response.resultData!!.id
                            )

                            SharedPrefClass().putObject(
                                this, GlobalConstants.USERDATA,
                                response.resultData!!
                            )

                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.ACCESS_TOKEN,
                                response.resultData!!.token
                            )
                            SharedPrefClass().putObject(
                                MyApplication.instance.applicationContext,
                                "isLogin",
                                true
                            )
                            val intent = Intent(this, SiteInfoActivity::class.java)
                            startActivity(intent)
                            finish()


                        }
                        /* response.code == 204 -> {
                             FirebaseFunctions.sendOTP("signup", mJsonObject, this)
                         }*/
                        else -> showToastError(message)
                    }

                }
            })


        loginViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {
                val username = activityLoginbinding.etEmail.text.toString()
                val password = activityLoginbinding.etPassword.text.toString()

                when (it) {
                    "btn_login" -> {
                        when {
                            TextUtils.isEmpty(username) -> run {
                                activityLoginbinding.etEmail.requestFocus()
                                activityLoginbinding.etEmail.error =
                                    getString(R.string.empty) + " " + getString(
                                        R.string.usename
                                    )
                            }

                            TextUtils.isEmpty(password) -> run {
                                activityLoginbinding.etPassword.requestFocus()
                                activityLoginbinding.etPassword.error =
                                    getString(R.string.empty) + " " + getString(
                                        R.string.password
                                    )
                            }
                            else -> {
                                mJsonObject.addProperty("username", username)
                                mJsonObject.addProperty("password", password)
                                // mJsonObject.addProperty("app-version", versionName)
                                loginViewModel.login(mJsonObject)

                            }
                        }
                    }
                }

            })
        )


        loginViewModel.isLoading().observe(this, Observer<Boolean> { aBoolean->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })

    }

}