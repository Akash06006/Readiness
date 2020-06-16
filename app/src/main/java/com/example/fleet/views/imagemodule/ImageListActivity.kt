package com.e.dummyproject

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.databinding.ActivityImageListBinding
import com.example.fleet.model.CategoriesType
import com.example.fleet.model.ImageListModel
import com.example.fleet.utils.BaseActivity
import com.example.fleet.utils.FileUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.soundcloud.android.crop.Crop
import java.io.ByteArrayOutputStream


class ImageListActivity : BaseActivity() {
    var adapter : ImageListAdapter? = null
    var openCamera : FloatingActionButton? = null
    var rvPublic : RecyclerView? = null
    val MY_CAMERA_PERMISSION_CODE = 1001
    val CAMERA_REQUEST = 1000
    var imageList : ArrayList<ImageListModel>? = null
    var images : ArrayList<CategoriesType.Images>? = null
    var noRecord : TextView? = null
//    var categories=""
//    var categoriesId=""

    var outPath : Uri? = null
    var categoriesName : TextView? = null
    private lateinit var binding : ActivityImageListBinding


    companion object {
        var categories = ""
        var categoriesId = ""
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initViews() {
        binding = viewDataBinding as ActivityImageListBinding

        rvPublic = findViewById(R.id.rvPublic)
        openCamera = findViewById(R.id.openCamera)
        noRecord = findViewById(R.id.noRecordFound)
        images = ArrayList()
        setAdapterData(images)
        categoriesName = findViewById(R.id.categoriesName)
        imageList = ArrayList()
        if (intent.getStringExtra("categoryName") != null) {

            categoriesName = findViewById(R.id.categoriesName)
            binding.toolbarCommon.imgLogout.visibility = View.GONE
            if (intent.getStringExtra("categoryName") != null) {

                categoriesName!!.setText(intent.getStringExtra("categoryName"))
                categories = intent.getStringExtra("categoryName")
                categoriesId = intent.getStringExtra("categoriesId")
            }

            openCamera!!.setOnClickListener {
                if (this.checkAndRequestPermissions()) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST)
                }
            }

            binding.imgCamera.setOnClickListener {
//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
//                } else {
//                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST)
//                }

                if (this.checkAndRequestPermissions()) {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(cameraIntent, CAMERA_REQUEST)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setAdapterData(images : ArrayList<CategoriesType.Images>?) {
        if (images!!.size > 0) {
            binding.llNoRecordFound.visibility = View.GONE
            rvPublic!!.visibility = View.VISIBLE
            adapter = ImageListAdapter(this, images)
            val mLayoutManager = LinearLayoutManager(this)
            rvPublic!!.layoutManager = mLayoutManager
            rvPublic!!.adapter = adapter
        } else {
            noRecord!!.setText("Please upload survey photos for $categories")
            binding.llNoRecordFound.visibility = View.VISIBLE
            rvPublic!!.visibility = View.GONE

        }
    }

    fun deletePhoto(position : Int) {

        for (i in 0..MyApplication.instance.categoriesList!!.size - 1) {
            if (MyApplication.instance.categoriesList!!.get(i).categoryId.equals(categoriesId)) {
                val images = MyApplication.instance.categoriesList!![i].images
                images!!.removeAt(position)
                MyApplication.instance.categoriesList!![i].images = images
                setAdapterData(images)
            }
        }
    }

    fun getDataFromList() {

        for (i in 0..MyApplication.instance.categoriesList!!.size - 1) {
            if (MyApplication.instance.categoriesList!!.get(i).categoryId.equals(categoriesId)) {
                val images = MyApplication.instance.categoriesList!![i].images
                setAdapterData(images)
            }
        }
    }


    fun getImageUri(inContext : Context, inImage : Bitmap) : Uri? {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }


    override fun getLayoutId() : Int {
        return R.layout.activity_image_list
    }


    override fun onResume() {
        super.onResume()
        getDataFromList()
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === CAMERA_REQUEST && resultCode === Activity.RESULT_OK) {
            var bitmap = data!!.getExtras()!!.get("data") as Bitmap
            outPath = getImageUri(this, bitmap)!!

            var filePath = FileUtils.getPath(this, outPath)

            var intent = Intent(this, MainActivity::class.java)

            intent.putExtra("uri", filePath.toString())
            intent.putExtra("name", categories)
            intent.putExtra("categoriesId", categoriesId)
            startActivity(intent)

//            Crop.of(outPath, outPath).asSquare().start(this)

        } else if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("uri", outPath.toString())
            startActivity(intent)
        }
    }
}




