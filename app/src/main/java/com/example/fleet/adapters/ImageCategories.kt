package com.example.fleet.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.e.dummyproject.ImageListActivity
import com.e.dummyproject.SitePhotosActivity
import com.example.fleet.R
import com.example.fleet.model.ImageCategoriesResponse

class ImageCategories(var imageListActivity : SitePhotosActivity, var categoriesList : ArrayList<ImageCategoriesResponse.ResultData>?) : RecyclerView.Adapter<ImageCategories.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_image_categories, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        try {
            holder.radioButton!!.setText(categoriesList!!.get(position).categoryName)
            holder.radioButton!!.setOnClickListener {
                var intent= Intent(imageListActivity, ImageListActivity::class.java)
                imageListActivity.startActivity(intent)
            }
//
//            holder.parentLayout!!.setOnClickListener {
//                var intent=Intent(imageListActivity,SitePhotosActivity::class.java)
//                imageListActivity.startActivity(intent)
//            }


        } catch (e: Exception) {

        }

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


        init {
            parentLayout=itemView.findViewById(R.id.parentLayout)
            radioButton=itemView.findViewById(R.id.radioButton)
        }
    }
}