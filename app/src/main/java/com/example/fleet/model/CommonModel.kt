package com.example.fleet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CommonModel {

    @SerializedName("status")
    @Expose
    var status : Boolean? = null
    @SerializedName("statusCode")
    @Expose
    var statusCode : Int? = null
    @SerializedName("message")
    @Expose
    var message : String? = null
    @SerializedName("data")
    @Expose
    var data : Any? = null
/*"message": "Answer Saved",
    "status": true,
    "statusCode": 200,
    "resultData": "Answer Saved",
    "resourceType": null*/

}


