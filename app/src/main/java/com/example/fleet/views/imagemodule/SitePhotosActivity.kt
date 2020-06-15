package com.e.dummyproject

import android.app.Dialog
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.adapters.ImageCategories
import com.example.fleet.application.MyApplication
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.ActivitySitePhotosBinding
import com.example.fleet.model.*
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.utils.DialogClass
import com.example.fleet.utils.DialogssInterface
import com.example.fleet.viewmodels.ImageCategoryModel
import com.example.fleet.viewmodels.SitePhotoViewModel
import com.example.fleet.views.authentication.LoginActivity
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject


class SitePhotosActivity : BaseActivity(), DialogssInterface {
    private lateinit var imageCaegoryViewModel : ImageCategoryModel
    private lateinit var binding : ActivitySitePhotosBinding
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    var adapter : ImageCategories? = null
    var rvPublic : RecyclerView? = null
    var categoriesList : ArrayList<ImageCategoriesResponse.ResultData>? = null
    var categoriesModel : CategoriesType? = null
    private val mJsonObject = JsonObject()
    private lateinit var loginViewModel : SitePhotoViewModel
    private var sharedPrefClass : SharedPrefClass? = null


    override fun initViews() {

        binding = viewDataBinding as ActivitySitePhotosBinding
        categoriesList = ArrayList()
        rvPublic = binding.rvPublic
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

                        for (i in 0..categoriesList!!.size - 1) {
                            categoriesModel = CategoriesType()
                            categoriesModel!!.categoryName = categoriesList!!.get(i).categoryName
                            categoriesModel!!.categoryId = categoriesList!!.get(i).id

                            val imagies = CategoriesType.Images()
                            val list = ArrayList<CategoriesType.Images>()
                            categoriesModel!!.images = list
                            MyApplication.instance.categoriesList!!.add(categoriesModel!!)
                        }
                        adapter!!.setList(categoriesList, MyApplication.instance.categoriesList!!)
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

                    "btn_submit" -> {
//                        val intent = Intent(this, ImageListActivity::class.java)
//                        startActivity(intent)

                        hitApi()
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

    override fun onResume() {
        super.onResume()
        adapter!!.notifyDataSetChanged()

    }


    fun hitApi() {

        try {
            val jsonArray = JSONArray()
            val studentsObj = JSONObject()
            var finalList = ArrayList<SitePhotoInput.ImagesDetailInputModelList>()
            sharedPrefClass = SharedPrefClass()
          var  userId = sharedPrefClass!!.getPrefValue(this,  GlobalConstants.USERID).toString()
          var  Location = sharedPrefClass!!.getPrefValue(this,  GlobalConstants.POC_ADDRESS).toString()
          var  siteId = sharedPrefClass!!.getPrefValue(this,  GlobalConstants.SURVEY_ID).toString()


            for (i in 0..MyApplication.instance.categoriesList!!.size - 1) {
                val images = MyApplication.instance.categoriesList!![i].images

                if (images!!.size > 0) {
                    for (j in 0..images!!.size - 1) {
                        var submisionModel = SitePhotoInput.ImagesDetailInputModelList()
                        submisionModel.CategoryId = MyApplication.instance.categoriesList!!.get(i).categoryId
                        submisionModel.Description = images.get(j).description
                        submisionModel.IsDeleted = "false"
                        submisionModel.Name = images.get(j).imageName
                        submisionModel.Path = images.get(j).imagePath
                        submisionModel.UserId = userId
                        submisionModel.Location = Location
                        submisionModel.SiteDetailId = siteId
                        finalList.add(submisionModel)
                    }
                }
            }
            try {

                for (i in 0 until finalList.size) {
                    var student2 = JSONObject()
                    student2.put("CategoryId", finalList.get(i).CategoryId)
                    student2.put("Description", finalList.get(i).Description)
                    student2.put("IsDeleted", finalList.get(i).IsDeleted)
                    student2.put("Name", finalList.get(i).Name)
                    student2.put("Path", finalList.get(i).Path)
                    student2.put("UserId", finalList.get(i).UserId)
                    student2.put("Location", finalList.get(i).Location)
                    student2.put("SiteDetailId", finalList.get(i).SiteDetailId)
                    jsonArray.put(student2);
                }

                studentsObj.put("ImagesDetailInputModel", jsonArray)

            } catch (e : Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }


//            mJsonObject.addProperty("ImagesDetailInputModel", finalList)
//        mJsonObject.addProperty("password", password)
            // mJsonObject.addProperty("app-version", versionName)


            loginViewModel = ViewModelProviders.of(this).get(SitePhotoViewModel::class.java)
            loginViewModel.siteParms(studentsObj)

            loginViewModel.siteResponse().observe(this,
                Observer<SitePhotoResoponse> { response->
                    stopProgressDialog()
                    if (response != null) {

                        loginViewModel.updateServeyId(siteId)

                        loginViewModel.serveyDetil().observe(this,
                            Observer<ServeyDetailResponse> { response->
                                stopProgressDialog()
                                if (response != null) {
                                    Toast.makeText(this,"status update",Toast.LENGTH_LONG)
                                }})


//                    val message = response.message
//                    when {
//                        response.code == 200 -> {
//
//
//                        }
//
//                        else -> showToastError(message)
//                    }

                    }
                })

//

        } catch (e : Exception) {

        }
    }


    override fun getLayoutId() : Int {
        return R.layout.activity_site_photos
    }


    fun setadapter() {
        adapter = ImageCategories(this, categoriesList, MyApplication.instance.categoriesList)
        rvPublic!!.setLayoutManager(GridLayoutManager(this, 2));
        rvPublic!!.adapter = adapter
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 120) {
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


