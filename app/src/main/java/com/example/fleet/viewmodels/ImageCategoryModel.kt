package com.example.fleet.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.ImageCategoriesResponse
import com.example.fleet.model.ImagesInput
import com.example.fleet.model.ServeyDetailResponse
import com.example.fleet.model.SitePhotoResoponse
import com.example.fleet.repositories.ImageCategoriesRepository
import com.example.fleet.repositories.SitePhotoRepository

class ImageCategoryModel : BaseViewModel() {
    private var model = MutableLiveData<ImageCategoriesResponse>()
    var repository : ImageCategoriesRepository? = null
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()
    private var loginResposne = MutableLiveData<SitePhotoResoponse>()
    private var loginRepository = SitePhotoRepository()
    private var serveyDetail = MutableLiveData<ServeyDetailResponse>()

    init {
        model = MutableLiveData()
        repository = ImageCategoriesRepository()
        model = repository!!.getLoginData()
        mIsUpdating.postValue(true)
        loginResposne = loginRepository.getData(null)
        serveyDetail = loginRepository.serveyDetailResponse(null)

    }

    fun getData() : LiveData<ImageCategoriesResponse> {
        return model
    }

    fun siteResponse() : LiveData<SitePhotoResoponse> {
        return loginResposne
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return btnClick
    }


    fun serveyDetil() : LiveData<ServeyDetailResponse> {
        return serveyDetail
    }

    fun updateServeyId(serveyId : String) {
        if (UtilsFunctions.isNetworkConnected()) {
            serveyDetail = loginRepository.serveyDetailResponse(serveyId)
            mIsUpdating.postValue(true)
        }
    }

    //fun siteParms(mJsonObject : JSONObject) {
    fun siteParms(mJsonObject : ImagesInput) {
        if (UtilsFunctions.isNetworkConnected()) {
            loginResposne = loginRepository.getData(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }
}