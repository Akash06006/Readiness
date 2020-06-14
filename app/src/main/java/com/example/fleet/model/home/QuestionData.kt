package com.example.fleet.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QuestionData {
    @SerializedName("statusCode")
    @Expose
    var statusCode : Int? = null
    @SerializedName("code")
    @Expose
    var code : Int? = null
    @SerializedName("message")
    @Expose
    var message : String? = null
    @SerializedName("resultData")
    @Expose
    var data : ArrayList<Data>? = null

    class Data {
        @SerializedName("id")
        @Expose
        var id : String? = null
        @SerializedName("question")
        @Expose
        var question : String? = null
        @SerializedName("selected")
        @Expose
        var selected : String? = null
        @SerializedName("isDeleted")
        @Expose
        var isDeleted : Boolean? = null


    }

}
