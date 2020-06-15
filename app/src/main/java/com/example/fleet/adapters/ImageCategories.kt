package com.example.fleet.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.dummyproject.ImageListActivity
import com.e.dummyproject.SitePhotosActivity
import com.example.fleet.R
import com.example.fleet.model.CategoriesType
import com.example.fleet.model.ImageCategoriesResponse

class ImageCategories(var imageListActivity : SitePhotosActivity, var categoriesList : ArrayList<ImageCategoriesResponse.ResultData>?, var categoriesType : ArrayList<CategoriesType>?) : RecyclerView.Adapter<ImageCategories.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_image_categories, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        try {
            holder.tvCategoryName!!.setText(categoriesList!!.get(position).categoryName)
            holder.parentLayout!!.setOnClickListener {
                var intent= Intent(imageListActivity, ImageListActivity::class.java)
                intent.putExtra("categoryName",categoriesList!!.get(position).categoryName.toString())
                intent.putExtra("categoriesId",categoriesList!!.get(position).id.toString())
                holder.ivIcon!!.setBackgroundResource(R.drawable.ic_active_radio)
                imageListActivity.startActivityForResult(intent,120)
            }
//
//            holder.parentLayout!!.setOnClickListener {
//                var intent=Intent(imageListActivity,SitePhotosActivity::class.java)
//                imageListActivity.startActivity(intent)
//            }


        } catch (e: Exception) {

        }

    }
    fun setList(categoriesList : ArrayList<ImageCategoriesResponse.ResultData>?, categoriesType : ArrayList<CategoriesType>){
        this.categoriesList=categoriesList
        this.categoriesType=categoriesType
        notifyDataSetChanged()

    }

    fun updateCategories(categoriesList : ArrayList<CategoriesType>?) {
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return categoriesList!!.size
    }


    inner class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var parentLayout: RelativeLayout?=null
        var radioButton:RadioButton?=null
        var tvCategoryName:TextView?=null
        var ivIcon:ImageView?=null
        init {
            parentLayout=itemView.findViewById(R.id.parentLayout)
            radioButton=itemView.findViewById(R.id.radioButton)
            tvCategoryName=itemView.findViewById(R.id.tvCategoryName)
            ivIcon=itemView.findViewById(R.id.ivIcon)
        }
    }
}