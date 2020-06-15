package com.e.dummyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.adapters.ImageCategories
import com.example.fleet.model.ImageCategoriesResponse
import com.example.fleet.utils.BaseActivity
import com.example.fleet.viewmodels.ImageCategoryModel

class SitePhotosActivity : BaseActivity(), View.OnClickListener{
    var btnSubmt: Button? = null
    private lateinit var imageCaegoryViewModel : ImageCategoryModel

//    var radioGrop1: RadioGroup? = null
//    var radioGrop2: RadioGroup? = null
//    var radioGrop3: RadioGroup? = null

    var rvExterior:RadioButton?=null
    var rvinterior:RadioButton?=null
    var parking:RadioButton?=null
    var ivCenter:RadioButton?=null
    var rbStreet:RadioButton?=null
    var adapter:ImageCategories?=null
    var rvPublic:RecyclerView?=null
    var categoriesList:ArrayList<ImageCategoriesResponse.ResultData>?=null
    override fun initViews() {
        categoriesList=ArrayList()


        btnSubmt = findViewById(R.id.btnSubmit)
        rvPublic = findViewById(R.id.rvPublic)
        btnSubmt!!.setOnClickListener {
            var intent = Intent(this, ImageListActivity::class.java)
            startActivity(intent)
        }
        setadapter()
        imageCaegoryViewModel = ViewModelProviders.of(this).get(ImageCategoryModel::class.java)
        imageCaegoryViewModel.getData().observe(this,
            Observer<ImageCategoriesResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message

                    if(response.statusCode.equals("200")){
                        categoriesList=response.resultData

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

    }

    override fun getLayoutId() : Int {
      return  R.layout.activity_site_photos
    }



    fun setadapter() {
        adapter=ImageCategories( this,categoriesList)
        rvPublic!!.setLayoutManager( GridLayoutManager(this, 2));

        rvPublic!!.adapter = adapter
    }


    override fun onClick(p0: View?) {

       }
    }


