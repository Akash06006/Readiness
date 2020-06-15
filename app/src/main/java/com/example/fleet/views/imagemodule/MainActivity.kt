package com.e.dummyproject

import android.os.Build
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.databinding.ActivityMainBinding
import com.example.fleet.model.CategoriesType
import com.example.fleet.utils.BaseActivity


class MainActivity : BaseActivity() {
    var btnUpload : Button? = null
    var image : ImageView? = null
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
            var list = ArrayList<CategoriesType.Images>()
            val imagies = CategoriesType.Images()
            imagies.imageName = intent.getStringExtra("categoriesId")
            imagies.imagePath = intent.getStringExtra("uri")

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
    }

    override fun getLayoutId() : Int {
        return R.layout.activity_main
    }
}
