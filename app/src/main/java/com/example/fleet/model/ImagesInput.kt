package com.example.fleet.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ImagesInput {

    @SerializedName("ImagesDetailInputModel")
    @Expose
    var data : ArrayList<ImagesDetailInputModel>? = null
}

class ImagesDetailInputModel {
    @SerializedName("IsDeleted")
    @Expose
    var IsDeleted : String? = null
    @SerializedName("SiteDetailId")
    @Expose
    var SiteDetailId : String? = null
    @SerializedName("Description")
    @Expose
    var Description : String? = null
    @SerializedName("UserId")
    @Expose
    var UserId : String? = null
    @SerializedName("CategoryId")
    @Expose
    var CategoryId : String? = null
    @SerializedName("Path")
    @Expose
    var Path : String? = null
    @SerializedName("Name")
    @Expose
    var Name : String? = null
    @SerializedName("Location")
    @Expose
    var Location : String? = null


}
