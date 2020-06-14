package com.example.fleet.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CommonModel
import com.example.fleet.model.LoginResponse
import com.example.fleet.model.SiteInfo
import com.example.fleet.repositories.LoginRepository
import com.example.fleet.repositories.SiteRepository
import com.google.gson.JsonObject
import org.json.JSONObject

class SiteInfoViewModel : BaseViewModel() {
    private var siteResposne = MutableLiveData<SiteInfo>()
    private var siteRepository = SiteRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        siteResposne = siteRepository.getSiteInfo(null)
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }


     fun siteResposne() : LiveData<SiteInfo> {
        return siteResposne
    }
    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]

    }

    fun getSiteInfo(userId : String) {
        if (UtilsFunctions.isNetworkConnected()) {
            siteResposne = siteRepository.getSiteInfo(userId)
                mIsUpdating.postValue(true)
            }


    }

}