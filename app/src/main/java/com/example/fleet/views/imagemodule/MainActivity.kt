package com.e.dummyproject

import android.Manifest
import android.R.attr
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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


class MainActivity : AppCompatActivity() {
    var btnUpload: Button? = null
    var image:ImageView?=null


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.ivImage);
        btnUpload = findViewById(R.id.btnUpload);

        if(intent.getStringExtra("uri")!=null){
            Glide.with(this)
                .load(intent.getStringExtra("uri"))
                .into(image!!);
        }

        btnUpload!!.setOnClickListener {
            var intent=Intent(this,SitePhotosActivity::class.java)
            startActivity(intent)

        }


    }




}
