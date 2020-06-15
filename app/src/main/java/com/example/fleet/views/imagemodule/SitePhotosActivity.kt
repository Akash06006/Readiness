package com.e.dummyproject

import android.app.Dialog
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
import com.example.fleet.application.MyApplication
import com.example.fleet.databinding.ActivitySitePhotosBinding
import com.example.fleet.databinding.ActivitySurveyBinding
import com.example.fleet.model.CategoriesType
import com.example.fleet.model.ImageCategoriesResponse
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.utils.DialogClass
import com.example.fleet.utils.DialogssInterface
import com.example.fleet.viewmodels.ImageCategoryModel
import com.example.fleet.viewmodels.SiteInfoViewModel
import com.example.fleet.views.authentication.LoginActivity

class SitePhotosActivity : BaseActivity(), DialogssInterface {
    private lateinit var imageCaegoryViewModel : ImageCategoryModel
    private lateinit var binding : ActivitySitePhotosBinding
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    var adapter : ImageCategories? = null
    var rvPublic : RecyclerView? = null
    var categoriesList : ArrayList<ImageCategoriesResponse.ResultData>? = null
    var categoriesModel: CategoriesType?=null

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

                    if(response.statusCode.equals("200")){
                        categoriesList=response.resultData

                        for (i in 0..categoriesList!!.size-1 ){
                            categoriesModel= CategoriesType()
                            categoriesModel!!.categoryName=categoriesList!!.get(i).categoryName
                            categoriesModel!!.categoryId=categoriesList!!.get(i).id

                            val imagies=CategoriesType.Images()
                            val list=ArrayList<CategoriesType.Images>()
                            //imagies.imageName=""
                            //imagies.imagePath=""
                            //list.add(imagies)
                            categoriesModel!!.images=list
                            MyApplication.instance.categoriesList!!.add(categoriesModel!!)
                        }
                        adapter!!.setList(categoriesList,  MyApplication.instance.categoriesList!!)
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
                        val intent = Intent(this, ImageListActivity::class.java)
                        startActivity(intent)
                    }


                    "img_logout" -> {

                            confirmationDialog = mDialogClass.setDefaultDialog(
                                this,
                                this,
                                "logout",
                                getString(R.string.want_to_logout)
                            )
                            confirmationDialog!!.show()
                    }
                }

            })
        )



    }

    override fun getLayoutId() : Int {
        return R.layout.activity_site_photos
    }


    fun setadapter() {
        adapter=ImageCategories( this,categoriesList, MyApplication.instance.categoriesList)
        rvPublic!!.setLayoutManager(GridLayoutManager(this, 2));
        rvPublic!!.adapter = adapter
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==120){
            adapter!!.updateCategories(MyApplication.instance.categoriesList)
        }
    }

    override fun onDialogConfirmAction(mView : View?, mKey : String) {
        when (mKey) {
            "logout" -> {
                confirmationDialog?.dismiss()
                SharedPrefClass().putObject(
                    MyApplication.instance.applicationContext,
                    "isLogin",
                    false
                )
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        }
    }






    override fun onDialogCancelAction(mView : View?, mKey : String) {
        when (mKey) {
            "logout" -> confirmationDialog?.dismiss()
        }
    }


}


