package com.example.fleet.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CommonModel
import com.example.fleet.model.LoginResponse
import com.example.fleet.repositories.LoginRepository
import com.google.gson.JsonObject
import org.json.JSONObject

class LoginViewModel : BaseViewModel() {
    private var loginResposne = MutableLiveData<LoginResponse>()
    private var loginRepository = LoginRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        loginResposne = loginRepository.getLoginData(null)
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }


     fun loginResponse() : LiveData<LoginResponse> {
        return loginResposne
    }
    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]

    }

    fun login(mJsonObject : JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            //emialExistenceResponse = loginRepository.checkPhoneExistence(mJsonObject)
            loginResposne = loginRepository.getLoginData(mJsonObject)
                mIsUpdating.postValue(true)
            }


    }

}