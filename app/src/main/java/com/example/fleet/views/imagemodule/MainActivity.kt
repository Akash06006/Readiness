package com.e.dummyproject

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.model.CategoriesType
import com.squareup.okhttp.RequestBody
import okhttp3.MultipartBody
import java.io.File


class MainActivity : AppCompatActivity() {
    var btnUpload: Button? = null
    var image:ImageView?=null
    var categoriesId=""
    var categoriesName=""
    var imagePath=""
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.ivImage);
        btnUpload = findViewById(R.id.btnUpload);
        categoriesId=intent.getStringExtra("categoriesId")
        categoriesName=intent.getStringExtra("name")
        if(intent.getStringExtra("uri")!=null){
            imagePath=intent.getStringExtra("uri")
            Glide.with(this)
                .load(imagePath)
                .into(image!!);
        }

        btnUpload!!.setOnClickListener {
            var list = ArrayList<CategoriesType.Images>()
            val imagies = CategoriesType.Images()
            imagies.imageName = intent.getStringExtra("categoriesId")
            imagies.imagePath = intent.getStringExtra("uri")
            for (i in 0..MyApplication.instance.categoriesList!!.size-1){
                if(MyApplication.instance.categoriesList!!.get(i).categoryId.equals(intent.getStringExtra("categoriesId"))){
                    val imageModel = MyApplication.instance.categoriesList!![i]
                    val images = MyApplication.instance.categoriesList!![i].images
                    images!!.add(imagies)
                    MyApplication.instance.categoriesList!!.set(i, imageModel)
                }
            }
            finish()
        }


//        btnUpload!!.setOnClickListener {
//            var  imageModel= CategoriesType()
//            var imagies=CategoriesType.Images()
//            var list=ArrayList<CategoriesType.Images>()
//
//            imagies.imageName=categoriesName
//            imagies.imagePath=imagePath
//            list.add(imagies)
//            imageModel.images=list!!
//
//            for (i in 0..MyApplication.instance.categoriesList!!.size-1){
//                if(MyApplication.instance.categoriesList!!.get(i).categoryId.equals(categoriesId)){
//                    MyApplication.instance.categoriesList!!.set(i,imageModel)
//                }
//            }
//            finish()
//        }

//
//        val mHashMap = HashMap<String, RequestBody>()
//        mHashMap["firstName"] =""
//        var userImage: MultipartBody.Part? = null
//
//        if (UtilsFunctions.isNetworkConnected()) {
//            startProgressDialog()
//            profieViewModel.updateProfile(mHashMap, userImage)
//        }

    }
}
