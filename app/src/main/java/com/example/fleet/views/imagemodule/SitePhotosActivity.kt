package com.e.dummyproject

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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
import com.example.fleet.views.SiteInfoActivity
import com.example.fleet.views.authentication.LoginActivity
import com.google.gson.Gson
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
    var imagesInput = ImagesInput()
    var siteId = ""
    override fun onBackPressed() {


    }

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

                            //imagies.imageName=""
                            //imagies.imagePath=""
                            //list.add(imagies)
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

        imageCaegoryViewModel.serveyDetil().observe(this,
            Observer<ServeyDetailResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    showSurveySuccessDialog()
                    //Toast.makeText(this, "status update", Toast.LENGTH_LONG)
                }
            })


        imageCaegoryViewModel.siteResponse().observe(this,
            Observer<SitePhotoResoponse> { response->
                stopProgressDialog()

                if (response != null) {

                    // showToastError(response.message)

                    if (response.statusCode == 200) {
                        MyApplication.instance.categoriesList!!.clear()
                        imageCaegoryViewModel.updateServeyId(siteId)

                    } else {
                        showToastError(response.message)
                    }


                }
            })



        imageCaegoryViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {

                when (it) {

                    "btn_submit" -> {
                        if (MyApplication.instance.categoriesList!!.size > 0) {
                            confirmationDialog = mDialogClass.setDefaultDialog(
                                this,
                                this,
                                "confirm",
                                getString(R.string.want_to_submit)
                            )
                            confirmationDialog!!.show()
                        } else {
                            showToastError("Please select site images")
                        }


//                        val intent = Intent(this, ImageListActivity::class.java)
//                        startActivity(intent)


//                        confirmationDialog = mDialogClass.setTahnkyouDialog(
//                            this,
//                            this,
//                            "thankyou"
//                        )
//                        confirmationDialog!!.show()

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
        var imagesExist=false
        adapter!!.notifyDataSetChanged()
        for (i in 0..MyApplication.instance.categoriesList!!.size - 1) {
            val images = MyApplication.instance.categoriesList!![i].images
            if(images!!.size>0) imagesExist=true

        }
        if(imagesExist) binding.btnSubmit.visibility = View.VISIBLE
        else
            binding.btnSubmit.visibility = View.GONE

    }


    private fun showSurveySuccessDialog() {
        val siteName = SharedPrefClass()!!.getPrefValue(
            MyApplication.instance,
            GlobalConstants.SITE_NAME
        ).toString()
        // val siteName = "Akash"
        confirmationDialog = Dialog(this, R.style.transparent_dialog)
        confirmationDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(this),
                R.layout.survey_submitted_success_dialog,
                null,
                false
            )

        confirmationDialog?.setContentView(binding.root)
        confirmationDialog?.setCancelable(false)

        confirmationDialog?.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        confirmationDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val cancel = confirmationDialog?.findViewById<Button>(R.id.btnDone)
        val tvMessage = confirmationDialog?.findViewById<TextView>(R.id.tvMessage)
        tvMessage?.setText(tvMessage?.text.toString() + " " + siteName)
        cancel?.setOnClickListener {

            val intent = Intent(this, SiteInfoActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        confirmationDialog?.show()
    }

    fun hitApi() {
        try {
            val jsonArray = JSONArray()
            val studentsObj = JSONObject()
            var finalList = ArrayList<SitePhotoInput.ImagesDetailInputModelList>()
            sharedPrefClass = SharedPrefClass()
            var userId = sharedPrefClass!!.getPrefValue(this, GlobalConstants.USERID).toString()
            var Location = sharedPrefClass!!.getPrefValue(this, GlobalConstants.FAC_ADDRESS).toString()
            siteId = sharedPrefClass!!.getPrefValue(this, GlobalConstants.SURVEY_ID).toString()


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

                val imageValues = ArrayList<ImagesDetailInputModel>()
                for (i in 0 until finalList.size) {

                    val data = ImagesDetailInputModel()
                    data.CategoryId = finalList.get(i).CategoryId.toString()
                    data.Description = finalList.get(i).Description
                    data.IsDeleted = finalList.get(i).IsDeleted
                    data.Location = finalList.get(i).Location
                    data.Name = finalList.get(i).Name
                    data.Path = finalList.get(i).Path
                    data.UserId = finalList.get(i).UserId.toString()
                    data.SiteDetailId = finalList.get(i).SiteDetailId.toString()
                    imageValues.add(data)

                    /* var student2 = JsonObject()
                     student2.addProperty("categoryId", finalList.get(i).CategoryId.toString())
                     student2.addProperty("description", finalList.get(i).Description)
                     student2.addProperty("isDeleted", finalList.get(i).IsDeleted)
                     student2.addProperty("name", finalList.get(i).Name)
                     //student2.put("path", finalList.get(i).Path)
                     student2.addProperty("path", finalList.get(i).Name)
                     student2.addProperty("userId", finalList.get(i).UserId.toString())
                     student2.addProperty("location", finalList.get(i).Location)
                     student2.addProperty("siteDetailId", finalList.get(i).SiteDetailId.toString())
                     jsonArray.put(student2);*/
                }

                imagesInput.data = imageValues
                val gson = Gson()
                val json = gson.toJson(imagesInput)
                studentsObj.put("ImagesDetailInputModel", jsonArray)

            } catch (e : Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }



            imageCaegoryViewModel.siteParms(imagesInput/*studentsObj*/)


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


                MyApplication.instance.categoriesList!!.clear()
                showToastSuccess(getString(R.string.logout_success))
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            "confirm" -> {
                confirmationDialog!!.dismiss()

                hitApi()

            }

            "thankyou" -> {
                confirmationDialog!!.dismiss()
                startActivity(Intent(this, SiteInfoActivity::class.java))
                finish()
            }


        }
    }


    override fun onDialogCancelAction(mView : View?, mKey : String) {
        when (mKey) {
            "logout" -> confirmationDialog?.dismiss()
            "thankyou" -> confirmationDialog?.dismiss()
            "confirm" -> confirmationDialog?.dismiss()

        }
    }


}
