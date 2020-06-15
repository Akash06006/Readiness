package com.example.fleet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SiteInfo {
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
    var resultData : List<ResultDatum>? = null

    @SerializedName("resourceType")
    @Expose
    var resourceType : Any? = null

    inner class ResultDatum {
        @SerializedName("userId")
        @Expose
        var userId : String? = null

        @SerializedName("facilityName")
        @Expose
        var facilityName : String? = null


        @SerializedName("siteName")
        @Expose
        var siteName : String? = null


        @SerializedName("facilityAddress")
        @Expose
        var facilityAddress : String? = null

        @SerializedName("pocName")
        @Expose
        var pocName : String? = null

        @SerializedName("pocAddress")
        @Expose
        var pocAddress : String? = null

        @SerializedName("pocPhoneNumber")
        @Expose
        var pocPhoneNumber : String? = null

        @SerializedName("pocEmail")
        @Expose
        var pocEmail : String? = null

        @SerializedName("id")
        @Expose
        var id : String? = null

        @SerializedName("createdBy")
        @Expose
        var createdBy : String? = null

        @SerializedName("createdDate")
        @Expose
        var createdDate : String? = null

        @SerializedName("modifiedBy")
        @Expose
        var modifiedBy : String? = null

        @SerializedName("modifiedDate")
        @Expose
        var modifiedDate : String? = null

        @SerializedName("isDeleted")
        @Expose
        var isDeleted : Boolean? = null

    }
}