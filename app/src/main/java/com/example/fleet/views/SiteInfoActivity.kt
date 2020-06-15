package com.example.fleet.views

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.ActivitySurveyBinding
import com.example.fleet.model.SiteInfo
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.utils.DialogClass
import com.example.fleet.utils.DialogssInterface
import com.example.fleet.viewmodels.SiteInfoViewModel
import com.example.fleet.views.authentication.LoginActivity
import com.example.fleet.views.home.DashboardActivity

class SiteInfoActivity : BaseActivity(), DialogssInterface {
    private lateinit var binding : ActivitySurveyBinding
    private var sharedPrefClass : SharedPrefClass? = null
    private var mDialogClass = DialogClass()
    private lateinit var siteInfoVM : SiteInfoViewModel
    private var confirmationDialog : Dialog? = null

    override fun getLayoutId() : Int {
        return R.layout.activity_survey
    }

    override fun initViews() {
        binding = viewDataBinding as ActivitySurveyBinding
        siteInfoVM = ViewModelProviders.of(this).get(SiteInfoViewModel::class.java)
        binding.siteVMModel = siteInfoVM
        sharedPrefClass = SharedPrefClass()
        binding.toolbarCommon.tvAddress.visibility=View.GONE

        val userId = sharedPrefClass!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.USERID
        ).toString()

        siteInfoVM.getSiteInfo(userId)


        siteInfoVM.siteResposne().observe(this,
            Observer<SiteInfo> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            binding.siteModel = response.resultData!!.get(0)
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.POC_ADDRESS,
                                response.resultData!![0].pocAddress
                            )
                            SharedPrefClass().putObject(
                                this,
                                GlobalConstants.SURVEY_ID,
                                response.resultData!![0].id
                            )

                        }
                        response.code == 401 -> {
                            UtilsFunctions.showToastError("Session Expired, Please login again")
                            SharedPrefClass().putObject(
                                MyApplication.instance.applicationContext,
                                "isLogin",
                                false
                            )
                            val i = Intent(
                                MyApplication.instance.applicationContext,
                                LoginActivity::class.java
                            )
                            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            MyApplication.instance.applicationContext.startActivity(i)
                        }

                        else -> showToastError(message)
                    }

                }
            })



        siteInfoVM.isLoading().observe(this, Observer<Boolean> { aBoolean->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })


        siteInfoVM.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {

                when (it) {
                    "img_logout" -> {
                        confirmationDialog = mDialogClass.setDefaultDialog(
                            this,
                            this,
                            "logout",
                            getString(R.string.want_to_logout)
                        )
                        confirmationDialog!!.show()
                    }
                    "btn_submit" -> {
                        val intent=Intent(this, DashboardActivity::class.java)
                        startActivity(intent)

                    }
                }

            })
        )


    }

    override fun onDialogConfirmAction(mView : View?, mKey : String) {
        when (mKey) {
            "logout" -> {
                confirmationDialog?.dismiss()
                SharedPrefClass().putObject(
                    MyApplication.instance.applicationContext,
                    "isLogin",
                    true
                )
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }

    override fun onDialogCancelAction(mView : View?, mKey : String) {
        when (mKey) {
            "logout" -> confirmationDialog?.dismiss()
        }
    }


}
