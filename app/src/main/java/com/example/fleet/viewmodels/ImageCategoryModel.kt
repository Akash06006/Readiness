package com.example.fleet.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fleet.model.ImageCategoriesResponse
import com.example.fleet.model.LoginResponse
import com.example.fleet.repositories.ImageCategoriesRepository

class ImageCategoryModel:ViewModel() {
    private var model = MutableLiveData<ImageCategoriesResponse>()
    var repository: ImageCategoriesRepository?=null
    init {
        model= MutableLiveData()
        repository=ImageCategoriesRepository()
        model = repository!!.getLoginData()
    }
    fun getData():LiveData<ImageCategoriesResponse>{
        return model
    }
}