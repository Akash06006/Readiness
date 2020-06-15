package com.e.dummyproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.model.CategoriesType

class ImageListAdapter(var imageListActivity : ImageListActivity,var images : ArrayList<CategoriesType.Images>?) : RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {

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

            Glide.with(imageListActivity)
                .load(images!!.get(position).imagePath)
                .into(holder.image!!);
            holder.tvDescription!!.setText(images!!.get(position).description)

        } catch (e: Exception) {

        }

    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return images!!.size
    }


    inner class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        var parentLayout:RelativeLayout?=null
        var tvDescription:TextView?=null
        var image:com.makeramen.roundedimageview.RoundedImageView?=null


        init {
            parentLayout=itemView.findViewById(R.id.parentLayout)
            image=itemView.findViewById(R.id.image)
            tvDescription=itemView.findViewById(R.id.tvDescription)
        }
    }
}