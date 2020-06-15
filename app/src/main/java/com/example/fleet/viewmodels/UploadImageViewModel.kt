package com.example.fleet.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.UploadImageResponse
import com.example.fleet.repositories.UploadImageRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadImageViewModel:BaseViewModel() {

    private val btnClick = MutableLiveData<String>()
    private val mIsUpdating = MutableLiveData<Boolean>()
    var repo: UploadImageRepository?=null
    private var data : MutableLiveData<UploadImageResponse>? = null



    init {
        repo=UploadImageRepository()
        data=MutableLiveData()
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
    }


    fun updateProfile(hashMap : HashMap<String, RequestBody>, image : MultipartBody.Part?) {
        if (UtilsFunctions.isNetworkConnected()) {
            data = repo!!.updateUserProfile(hashMap, image)
            mIsUpdating.postValue(true)
        }
    }
}