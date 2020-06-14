package com.e.dummyproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R

class ImageListAdapter(var imageListActivity: ImageListActivity) : RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.raw_image_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        try {
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
        return 20
    }


    inner class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var parentLayout:RelativeLayout?=null


        init {
            parentLayout=itemView.findViewById(R.id.parentLayout)
        }
    }
}