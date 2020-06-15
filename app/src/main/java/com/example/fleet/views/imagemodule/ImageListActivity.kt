package com.e.dummyproject

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.model.CategoriesType
import com.example.fleet.model.ImageListModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.soundcloud.android.crop.Crop
import java.io.ByteArrayOutputStream


class ImageListActivity : AppCompatActivity() {
    var adapter: ImageListAdapter? = null
    var openCamera: FloatingActionButton? = null
    var rvPublic: RecyclerView? = null
    val MY_CAMERA_PERMISSION_CODE = 1001
    val CAMERA_REQUEST = 1000
    var outPath:Uri?=null
    var categoriesName:TextView?=null
    var imageList:ArrayList<ImageListModel>?=null
   var images : ArrayList<CategoriesType.Images>?=null
//    var categories=""
//    var categoriesId=""

    companion object{
        var categories=""
        var categoriesId=""
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        rvPublic = findViewById(R.id.rvPublic)
        openCamera = findViewById(R.id.openCamera)
        images= ArrayList()
        setAdapterData(images)
        categoriesName=findViewById(R.id.categoriesName)
        imageList= ArrayList()
        if(intent.getStringExtra("categoryName")!=null){
            categoriesName!!.setText(intent.getStringExtra("categoryName"))
            categories=intent.getStringExtra("categoryName")
            categoriesId=intent.getStringExtra("categoriesId")
        }

        openCamera!!.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }
    }
    fun setAdapterData(images : ArrayList<CategoriesType.Images>?) {
        adapter = ImageListAdapter(this,images)
        val mLayoutManager = LinearLayoutManager(this)
        rvPublic!!.layoutManager = mLayoutManager
        rvPublic!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getDataFromList()
    }


    fun getDataFromList(){

        for (i in 0..MyApplication.instance.categoriesList!!.size-1){
            if(MyApplication.instance.categoriesList!!.get(i).categoryId.equals(categoriesId)){
                val images = MyApplication.instance.categoriesList!![i].images
                setAdapterData(images)
            }
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === CAMERA_REQUEST && resultCode === Activity.RESULT_OK) {
            var bitmap = data!!.getExtras()!!.get("data") as Bitmap
            outPath = getImageUri(this, bitmap)!!
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("uri", outPath.toString())
            intent.putExtra("name",categories)
            intent.putExtra("categoriesId",categoriesId)
            startActivity(intent)

//            Crop.of(outPath, outPath).asSquare().start(this)

        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("uri", outPath.toString())
            startActivity(intent)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
}
