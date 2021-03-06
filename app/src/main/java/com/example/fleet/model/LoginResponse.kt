package com.example.fleet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {
    @SerializedName("message")
    @Expose
    var message : String? = null

    @SerializedName("status")
    @Expose
    var status : Boolean? = null

    @SerializedName("statusCode")
    @Expose
    var code : Int? = null

    @SerializedName("resultData")
    @Expose
    var resultData : ResultData? = null

    @SerializedName("resourceType")
    @Expose
    var resourceType : Any? = null

    public class ResultData {
        @SerializedName("userName")
        @Expose
        var userName : String? = null

        @SerializedName("password")
        @Expose
        var password : Any? = null

        @SerializedName("token")
        @Expose
        var token : String? = null

        @SerializedName("id")
        @Expose
        var id : String? = null

        @SerializedName("createdBy")
        @Expose
        var createdBy : Any? = null

        @SerializedName("createdDate")
        @Expose
        var createdDate : String? = null

        @SerializedName("modifiedBy")
        @Expose
        var modifiedBy : Any? = null

        @SerializedName("modifiedDate")
        @Expose
        var modifiedDate : Any? = null

        @SerializedName("isDeleted")
        @Expose
        var isDeleted : Boolean? = null

    }
}