package com.e.dummyproject

import android.os.Build
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.databinding.ActivityMainBinding
import com.example.fleet.model.CategoriesType
import com.example.fleet.model.ImageCategoriesResponse
import com.example.fleet.model.UploadImageResponse
import com.example.fleet.utils.BaseActivity
import com.example.fleet.viewmodels.UploadImageViewModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class MainActivity : BaseActivity() {
    var btnUpload : Button? = null
    var image : ImageView? = null
    private lateinit var imageCaegoryViewModel : UploadImageViewModel

    private lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initViews() {
        image = findViewById(R.id.ivImage);
        btnUpload = findViewById(R.id.btnUpload);
        binding = viewDataBinding as ActivityMainBinding

        if (intent.getStringExtra("uri") != null) {
            Glide.with(this)
                .load(intent.getStringExtra("uri"))
                .into(image!!);
        }
        btnUpload!!.setOnClickListener {

            if(!binding.imgDescription.text.toString().trim().isEmpty()) {

//
//                var list = ArrayList<CategoriesType.Images>()
//                val imagies = CategoriesType.Images()
//                imagies.imageName = intent.getStringExtra("categoriesId")
//                imagies.imagePath = intent.getStringExtra("uri")
//                //  imagies.imagDescription = intent.getStringExtra("uri")
//
//                for (i in 0..MyApplication.instance.categoriesList!!.size - 1) {
//
//                    if (MyApplication.instance.categoriesList!!.get(i).categoryId.equals(intent.getStringExtra("categoriesId"))) {
//                        val imageModel = MyApplication.instance.categoriesList!![i]
//                        val images = MyApplication.instance.categoriesList!![i].images
//                        images!!.add(imagies)
//                        MyApplication.instance.categoriesList!!.set(i, imageModel)
//                    }
//                }

                var file = File(intent.getStringExtra("uri"))

//            val mHashMap = HashMap<String, RequestBody>()
//            mHashMap["file"] = createPartFromString("file")
                var userImage : MultipartBody.Part? = null
                userImage = prepareFilePart("file", file)

                if (UtilsFunctions.isNetworkConnected()) {
                    startProgressDialog()

                    imageCaegoryViewModel = ViewModelProviders.of(this).get(UploadImageViewModel::class.java)
                    imageCaegoryViewModel.updateProfile(userImage)
                    imageCaegoryViewModel.getData().observe(this,
                        Observer<UploadImageResponse> { response->
                            stopProgressDialog()
                            if (response != null) {
                                var list = ArrayList<CategoriesType.Images>()
                                val imagies = CategoriesType.Images()
                                imagies.imageName = response.fileName
                                imagies.imagePath = intent.getStringExtra("uri")
                                imagies.description = binding.imgDescription.text.toString().trim()
                                imagies.serverImagePath = response.path
                                //  imagies.imagDescription = intent.getStringExtra("uri")

                                for (i in 0..MyApplication.instance.categoriesList!!.size - 1) {

                                    if (MyApplication.instance.categoriesList!!.get(i).categoryId.equals(intent.getStringExtra("categoriesId"))) {
                                        val imageModel = MyApplication.instance.categoriesList!![i]
                                        val images = MyApplication.instance.categoriesList!![i].images
                                        images!!.add(imagies)
                                        MyApplication.instance.categoriesList!!.set(i, imageModel)
                                    }
                                }
                                finish()
                            }
                        })
                }

            } else{
                Toast.makeText(this,"Please enter description",Toast.LENGTH_LONG).show()
            }
        }

    }

//    fun createPartFromString(string: String): RequestBody {
//        return RequestBody.create(
//            MultipartBody.FORM, string
//        )
//    }
    fun prepareFilePart(partName: String, fileUri: File): MultipartBody.Part {
        val requestFile = RequestBody.create(
            MediaType.parse("image/*"),
            fileUri
        )
        return MultipartBody.Part.createFormData(partName, fileUri.name, requestFile)
    }

    override fun getLayoutId() : Int {
        return R.layout.activity_main
    }
}
