package com.example.fleet.api

import androidx.annotation.NonNull

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
* Created by Saira on 03/07/2019.
 */



class ApiService<T> {
    fun get(mApiResponse: ApiResponse<T>, methodName: Call<T>) {
        methodName.enqueue(object : Callback<T> {

            override fun onResponse(@NonNull call: Call<T>, @NonNull response: Response<T>) {
                mApiResponse.onResponse(response)
                print(response.body())
            }

            override fun onFailure(@NonNull call: Call<T>, @NonNull t: Throwable) {
                mApiResponse.onError(t.message.toString())
                println(t)
            }
        })
    }


}
