package com.example.fleet.repositories

import androidx.lifecycle.MutableLiveData
import com.example.fleet.R
import com.example.fleet.api.ApiClient
import com.example.fleet.api.ApiResponse
import com.example.fleet.api.ApiService
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.ServeyDetailResponse
import com.example.fleet.model.SiteInfo
import com.example.fleet.model.UploadImageResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Response


class UploadImageRepository {
    private val gson = GsonBuilder().serializeNulls().create()
    private var data : MutableLiveData<UploadImageResponse>? = null

    init {
        data = MutableLiveData()
    }

    fun updateUserProfile( image : MultipartBody.Part?
    ) : MutableLiveData<UploadImageResponse> {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)

                            gson.fromJson<UploadImageResponse>(
                                "" + mResponse.body(),
                                UploadImageResponse::class.java
                            )
                        else {
                            gson.fromJson<UploadImageResponse>(
                                mResponse.errorBody()!!.charStream(),
                                UploadImageResponse::class.java
                            )
                        }

                        if(mResponse.code()==200){
                            data!!.postValue(loginResponse)
                        }
                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)
                    }
                }, ApiClient.getApiInterface().callUpdateProfile(image)
            )

        return data!!
    }




}