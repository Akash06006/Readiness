package com.example.fleet.model

class ImageCategoriesResponse {
    var resultData : ArrayList<ResultData>? = null

    var message : String? = null

    var status : String? = null

    var statusCode : String? = null

    var resourceType : String? = null

    class ResultData {
        var createdDate : String? = null

        var isDeleted : String? = null

        var createdBy : String? = null

        var modifiedDate : String? = null

        var modifiedBy : String? = null

        var id : String? = null

        var categoryName : String? = null

    }

}