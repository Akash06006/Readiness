package com.example.fleet.model

class CategoriesType {

  var categoryName:String?=null
  var categoryId:String?=null
    var images:ArrayList<Images>?=null

    class Images{
        var imageName:String?=null
        var imagePath:String?=null
        var serverImagePath:String?=null
        var description:String?=null
    }

}