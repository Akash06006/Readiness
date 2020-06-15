package com.e.dummyproject

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.utils.BaseActivity
import com.example.fleet.databinding.ActivitySiteScoredBinding
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.DialogClass
import com.example.fleet.utils.DialogssInterface
import com.example.fleet.viewmodels.SiteInfoViewModel
import com.example.fleet.viewmodels.questions.ScoreViewModel
import com.example.fleet.views.authentication.LoginActivity
import com.example.fleet.views.home.DashboardActivity


class SiteScoredActivity : BaseActivity(), DialogssInterface {
    private lateinit var fragmentHomeBinding : ActivitySiteScoredBinding
    private var sharedPrefClass : SharedPrefClass? = null
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    private lateinit var scoreVMModel : ScoreViewModel

    override fun onBackPressed() {

    }

    override fun initViews() {
        fragmentHomeBinding = viewDataBinding as ActivitySiteScoredBinding
        sharedPrefClass = SharedPrefClass()
        scoreVMModel = ViewModelProviders.of(this).get(ScoreViewModel::class.java)
        fragmentHomeBinding.scoreVMModel = scoreVMModel

        scoreVMModel.isClick().observe(
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
                    "btnSubmt" -> {
                        val intent = Intent(this, SitePhotosActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                }

            })
        )


        //fragmentHomeBinding.txtAddress.setText(address)
    }

    override fun getLayoutId() : Int {
        return R.layout.activity_site_scored
    }

    override fun onDialogConfirmAction(mView : View?, mKey : String) {
        when (mKey) {
            "logout" -> {
                confirmationDialog?.dismiss()
                SharedPrefClass().putObject(
                    MyApplication.instance.applicationContext,
                    "isLogin",
                    false
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