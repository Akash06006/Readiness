package com.example.fleet.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleet.model.ImageCategoriesResponse
import com.example.fleet.model.LoginResponse
import com.example.fleet.repositories.ImageCategoriesRepository

class ImageCategoryModel:BaseViewModel() {
    private var model = MutableLiveData<ImageCategoriesResponse>()
    var repository: ImageCategoriesRepository?=null
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        model= MutableLiveData()
        repository=ImageCategoriesRepository()
        model = repository!!.getLoginData()
        mIsUpdating.postValue(true)
    }
    fun getData():LiveData<ImageCategoriesResponse>{
        return model
    }

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
    }
}