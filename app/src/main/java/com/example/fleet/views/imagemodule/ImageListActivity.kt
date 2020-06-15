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
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.oginotihiro.cropview.CropUtil
import com.oginotihiro.cropview.CropView
import com.soundcloud.android.crop.Crop
import java.io.ByteArrayOutputStream


class ImageListActivity : AppCompatActivity() {
    var adapter: ImageListAdapter? = null
    var openCamera: FloatingActionButton? = null
    var rvPublic: RecyclerView? = null
    val MY_CAMERA_PERMISSION_CODE = 1001
    val CAMERA_REQUEST = 1000
    var outPath:Uri?=null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_list)
        rvPublic = findViewById(R.id.rvPublic)
        openCamera = findViewById(R.id.openCamera)
        setAdapterData()

        openCamera!!.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
            } else {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }
    }

    fun setAdapterData() {
        adapter = ImageListAdapter(this)
        val mLayoutManager = LinearLayoutManager(this)
        rvPublic!!.layoutManager = mLayoutManager
        rvPublic!!.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode === CAMERA_REQUEST && resultCode === Activity.RESULT_OK) {
            var bitmap = data!!.getExtras()!!.get("data") as Bitmap

            outPath = getImageUri(this, bitmap)!!

            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("uri", outPath.toString())
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
