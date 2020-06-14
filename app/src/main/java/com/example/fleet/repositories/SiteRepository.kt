package com.example.fleet.repositories

import androidx.lifecycle.MutableLiveData
import com.example.fleet.R
import com.example.fleet.api.ApiClient
import com.example.fleet.api.ApiResponse
import com.example.fleet.api.ApiService
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CommonModel
import com.example.fleet.model.LoginResponse
import com.example.fleet.model.SiteInfo
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response

class SiteRepository {
    private var data : MutableLiveData<SiteInfo>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()

    }

    fun getSiteInfo(userId : String?) : MutableLiveData<SiteInfo> {
        if (userId != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<SiteInfo>("" + mResponse.body(), SiteInfo::class.java)
                        else {
                            gson.fromJson<SiteInfo>(
                                 mResponse.errorBody()!!.charStream(),
                                SiteInfo::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().callSiteInfo(userId = userId!!)

            )

        }
        return data!!

    }



}