package com.example.fleet.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.LoginResponse
import com.example.fleet.model.ServeyDetailResponse
import com.example.fleet.model.SitePhotoResoponse
import com.example.fleet.repositories.LoginRepository
import com.example.fleet.repositories.SitePhotoRepository
import com.google.gson.JsonObject
import org.json.JSONObject

class SitePhotoViewModel:BaseViewModel() {


    private var loginRepository = SitePhotoRepository()
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