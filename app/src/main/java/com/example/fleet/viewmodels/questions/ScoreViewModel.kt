package com.example.fleet.viewmodels.questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CommonModel
import com.example.fleet.model.LoginResponse
import com.example.fleet.model.SiteInfo
import com.example.fleet.repositories.LoginRepository
import com.example.fleet.repositories.SiteRepository
import com.example.fleet.viewmodels.BaseViewModel
import com.google.gson.JsonObject
import org.json.JSONObject

class ScoreViewModel : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()


    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }


    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]

    }


}