package com.e.dummyproject

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.model.CategoriesType


class MainActivity : AppCompatActivity() {
    var btnUpload : Button? = null
    var image : ImageView? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.ivImage);
        btnUpload = findViewById(R.id.btnUpload);

        if (intent.getStringExtra("uri") != null) {
            Glide.with(this)
                .load(intent.getStringExtra("uri"))
                .into(image!!);
        }
        btnUpload!!.setOnClickListener {
            var list = ArrayList<CategoriesType.Images>()
//
////           imagies.imageName=intent.getStringExtra("name")
//////          /
//////            list.add(imagies)

            val imagies = CategoriesType.Images()
            imagies.imageName = intent.getStringExtra("categoriesId")
            imagies.imagePath = intent.getStringExtra("uri")


            //val index = MyApplication.instance.categoriesList!!.find(intent.getStringExtra("categoriesId")) // pass value
            //val index: Event? =  MyApplication.instance.categoriesList!!.find { it.categoriesId == intent.getStringExtra("categoriesId") }

           // val imageModel = MyApplication.instance.categoriesList!![index]
           // val images = MyApplication.instance.categoriesList!![index].images
         //   images!!.add(imagies)
            //MyApplication.instance.categoriesList!!.set(index, imageModel)


//            for (i in 0..MyApplication.instance.categoriesList!!.size-1){
//
//                if(MyApplication.instance.categoriesList!!.get(i).categoryId.equals(intent.getStringExtra("categoriesId"))){
//                    MyApplication.instance.categoriesList!!.set(i,imageModel)
//                }
//            }

            finish()
        }
    }
}
