package com.example.fleet.api

import com.example.fleet.model.home.QuestionInput
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.HashMap

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("api/Account/Login")
    fun callLogin(@Body jsonObject : JsonObject) : Call<JsonObject>


    @GET("api/Question/GetQuestions")
    fun getQuestions(/*@Body mJsonObject : JsonObject*/) : Call<JsonObject>

    @POST("api/Question/SaveAnswer")
    fun saveAnswer(@Body mJsonObject : ArrayList<QuestionInput.AnswerInputModel>?) : Call<JsonObject>


    /*@POST("login/")
    fun callLogin(@Body jsonObject : JsonObject) : Call<JsonObject>*/

    @GET("api/logout")
    fun callLogout(/*@Body mJsonObject : JsonObject*/) : Call<JsonObject>

    @GET("api/Question/GetSurveyDetails")
    fun callSiteInfo(@Query("userId") userId : String) : Call<JsonObject>

    @GET("api/service/list")
    fun getServicesList(@Query("page") page : Int, @Query("limit") limit : Int, @Query("status") status : String) : Call<JsonObject>//(@Query("status") status : String) : Call<JsonObject>


}