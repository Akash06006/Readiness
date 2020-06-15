package com.example.fleet.repositories

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fleet.R
import com.example.fleet.api.ApiClient
import com.example.fleet.api.ApiResponse
import com.example.fleet.api.ApiService
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.ServeyDetailResponse
import com.example.fleet.model.SitePhotoResoponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Response


class SitePhotoRepository {


    private var data : MutableLiveData<SitePhotoResoponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()
    private var serveyDetail : MutableLiveData<ServeyDetailResponse>? = null

    init {
        data = MutableLiveData()
        serveyDetail = MutableLiveData()

    }

    fun getData(jsonObject : JSONObject?) : MutableLiveData<SitePhotoResoponse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JSONObject>()
            mApiService.get(
                object : ApiResponse<JSONObject> {
                    override fun onResponse(mResponse : Response<JSONObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<SitePhotoResoponse>("" + mResponse.body(), SitePhotoResoponse::class.java)
                        else {

                            val message : APIError = Gson().fromJson(mResponse.errorBody()!!.charStream(), APIError::class.java)

                            gson.fromJson<SitePhotoResoponse>(
                                mResponse.errorBody()!!.charStream(),
                                SitePhotoResoponse::class.java
                            )
                        }

                        data!!.postValue(loginResponse)
                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().sitePhoto(jsonObject)
            )
        }
        return data!!
    }


    fun serveyDetailResponse(userId : String?) : MutableLiveData<ServeyDetailResponse> {
        if (userId != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<ServeyDetailResponse>("" + mResponse.body(), ServeyDetailResponse::class.java)
                        else {

                           // Toast.makeText(this@MainActivity, "" + message.message, Toast.LENGTH_SHORT).show()

                            gson.fromJson<ServeyDetailResponse>(
                                mResponse.errorBody()!!.charStream(),
                                ServeyDetailResponse::class.java
                            )
                        }
                        serveyDetail!!.postValue(loginResponse)
                    }
                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        serveyDetail!!.postValue(null)
                    }
                }, ApiClient.getApiInterface().callServyDetail(userId = userId!!)
            )
        }
        return serveyDetail!!

    }

    class APIError {
        val message : String? = null
    }
}