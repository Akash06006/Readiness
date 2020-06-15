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
import com.example.fleet.viewmodels.ImageCategoryModel

class SitePhotosActivity : AppCompatActivity(), View.OnClickListener,
    RadioGroup.OnCheckedChangeListener {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site_photos)
        categoriesList=ArrayList()
        imageCaegoryViewModel = ViewModelProviders.of(this).get(ImageCategoryModel::class.java)
        imageCaegoryViewModel.getData().observe(this,
            Observer<ImageCategoriesResponse> { response->
                if (response != null) {
                    val message = response.message

                    if(response.statusCode.equals("200")){
                        categoriesList=response.resultData
                        setadapter(categoriesList)
                    }


                    Toast.makeText(this, "" + message, Toast.LENGTH_LONG).show()
                }
            })


        btnSubmt = findViewById(R.id.btnSubmit)
        btnSubmt!!.setOnClickListener {
            var intent = Intent(this, ImageListActivity::class.java)
            startActivity(intent)
        }

        rvExterior=findViewById(R.id.rvExterior)
        rvPublic=findViewById(R.id.rvPublic)
        rvinterior=findViewById(R.id.rvinterior)
        parking=findViewById(R.id.parking)
        ivCenter=findViewById(R.id.ivCenter)
        rbStreet=findViewById(R.id.rbStreet)

        rvExterior!!.setOnClickListener(this)
        rvinterior!!.setOnClickListener(this)
        parking!!.setOnClickListener(this)
        ivCenter!!.setOnClickListener(this)
        rbStreet!!.setOnClickListener(this)


//        radioGrop1 = findViewById(R.id.radioGrp1)
//        radioGrop2 = findViewById(R.id.radioGrp2)
//        radioGrop3 = findViewById(R.id.radioGrp3)
//        radioGrop1!!.setOnCheckedChangeListener(this)
//        radioGrop2!!.setOnCheckedChangeListener(this)
//        radioGrop3!!.setOnCheckedChangeListener(this)
    }


    fun setadapter(categoriesList : ArrayList<ImageCategoriesResponse.ResultData>?) {
        adapter=ImageCategories( this,categoriesList)
        //val mLayoutManager = LinearLayoutManager(this)
       // rvPublic!!.layoutManager = mLayoutManager
        rvPublic!!.setLayoutManager( GridLayoutManager(this, 2));

//        val mLayoutManager = LinearLayoutManager(this)
//        rvPublic!!.layoutManager = mLayoutManager

        rvPublic!!.adapter = adapter
    }


    override fun onClick(p0: View?) {
       when(p0!!.id){
//           R.id.rvExterior->{
//               var intent=Intent(this,ImageListActivity::class.java)
//               startActivity(intent)
//
//           }
//           R.id.rvinterior->{
//               var intent=Intent(this,ImageListActivity::class.java)
//               startActivity(intent)
//           }
//           R.id.parking->{
//               var intent=Intent(this,ImageListActivity::class.java)
//               startActivity(intent)
//           }
//           R.id.ivCenter->{
//               var intent=Intent(this,ImageListActivity::class.java)
//               startActivity(intent)
//           }
//           R.id.rbStreet->{
//               var intent=Intent(this,ImageListActivity::class.java)
//               startActivity(intent)
//           }

       }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
//        when (p0!!.id) {
//            R.id.radioGrp1 -> {
//                radioGrop2!!.setOnCheckedChangeListener(null)
//                radioGrop3!!.setOnCheckedChangeListener(null)
//                radioGrop2!!.clearCheck()
//                radioGrop3!!.clearCheck()
//                radioGrop2!!.setOnCheckedChangeListener(this)
//                radioGrop3!!.setOnCheckedChangeListener(this)
//            }
//            R.id.radioGrp2 -> {
//                radioGrop1!!.setOnCheckedChangeListener(null)
//                radioGrop3!!.setOnCheckedChangeListener(null)
//                radioGrop1!!.clearCheck()
//                radioGrop3!!.clearCheck()
//                radioGrop1!!.setOnCheckedChangeListener(this)
//                radioGrop3!!.setOnCheckedChangeListener(this)
//            }
//            R.id.radioGrp3 -> {
//                radioGrop1!!.setOnCheckedChangeListener(null)
//                radioGrop2!!.setOnCheckedChangeListener(null)
//                radioGrop1!!.clearCheck()
//                radioGrop2!!.clearCheck()
//                radioGrop1!!.setOnCheckedChangeListener(this)
//                radioGrop2!!.setOnCheckedChangeListener(this)
//            }
//
//        }
    }
}