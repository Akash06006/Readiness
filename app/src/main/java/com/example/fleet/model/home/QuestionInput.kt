package com.example.fleet.model.home

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class QuestionInput {

    @SerializedName("AnswerInputModel")
    @Expose
    var data : ArrayList<AnswerInputModel>? = null

    class AnswerInputModel {
        @SerializedName("QuestionId")
        @Expose
        var questionId : String? = null
        @SerializedName("Answer")
        @Expose
        var answer : String? = null
        @SerializedName("UserId")
        @Expose
        var userId : String? = null
        @SerializedName("SiteDetailId")
        @Expose
        var siteDetailId : String? = null


    }

}
