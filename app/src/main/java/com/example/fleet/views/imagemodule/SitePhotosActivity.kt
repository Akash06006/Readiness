package com.e.dummyproject

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.adapters.ImageCategories
import com.example.fleet.databinding.ActivitySitePhotosBinding
import com.example.fleet.databinding.ActivitySurveyBinding
import com.example.fleet.model.ImageCategoriesResponse
import com.example.fleet.utils.BaseActivity
import com.example.fleet.viewmodels.ImageCategoryModel
import com.example.fleet.viewmodels.SiteInfoViewModel

class SitePhotosActivity : BaseActivity() {
    private lateinit var imageCaegoryViewModel : ImageCategoryModel
    private lateinit var binding : ActivitySitePhotosBinding

    var adapter : ImageCategories? = null
    var rvPublic : RecyclerView? = null
    var categoriesList : ArrayList<ImageCategoriesResponse.ResultData>? = null
    override fun initViews() {

        binding = viewDataBinding as ActivitySitePhotosBinding
        categoriesList = ArrayList()
        rvPublic =binding.rvPublic
        setadapter()
        imageCaegoryViewModel = ViewModelProviders.of(this).get(ImageCategoryModel::class.java)
        binding.imageCaegoryViewModel = imageCaegoryViewModel

        imageCaegoryViewModel.getData().observe(this,
            Observer<ImageCategoriesResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message

                    if (response.statusCode.equals("200")) {
                        categoriesList = response.resultData

                        adapter!!.setList(categoriesList)
                    }
                }
            })

        imageCaegoryViewModel.isLoading().observe(this, Observer<Boolean> { aBoolean->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })

        imageCaegoryViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {

                when (it) {

                    "btnSubmt" -> {
                       // startActivity(Intent(this, SitePhotosActivity::class.java))

                    }
                }

            })
        )



    }

    override fun getLayoutId() : Int {
        return R.layout.activity_site_photos
    }


    fun setadapter() {
        adapter = ImageCategories(this, categoriesList)
        rvPublic!!.setLayoutManager(GridLayoutManager(this, 2));
        rvPublic!!.adapter = adapter
    }



}


