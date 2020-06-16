package com.e.dummyproject

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.databinding.RawImageListBinding
import com.example.fleet.model.CategoriesType
import com.example.fleet.utils.DialogClass
import com.example.fleet.utils.DialogssInterface

class ImageListAdapter(var imageListActivity : ImageListActivity, var images : ArrayList<CategoriesType.Images>?) : RecyclerView.Adapter<ImageListAdapter.MyViewHolder>(), DialogssInterface {
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    var position = -1
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : MyViewHolder {


        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.raw_image_list,
            parent,
            false
        ) as RawImageListBinding


        return MyViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder : MyViewHolder, position : Int) {

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
            holder.imgDelete!!.setOnClickListener({
                confirmationDialog = mDialogClass.setDefaultDialog(
                    imageListActivity,
                    this,
                    "delete",
                    imageListActivity.getString(R.string.want_to_delete)
                )
                confirmationDialog!!.show()
                this.position = position

            })


        } catch (e : Exception) {

        }

    }


    override fun getItemViewType(position : Int) : Int {
        return position
    }

    override fun getItemCount() : Int {
        return images!!.size
    }


    inner class MyViewHolder(
        itemView : View
    ) : RecyclerView.ViewHolder(itemView) {

        var parentLayout : RelativeLayout? = null
        var tvDescription : TextView? = null
        var image : com.makeramen.roundedimageview.RoundedImageView? = null
        var imgDelete : ImageView? = null


        init {
            parentLayout = itemView.findViewById(R.id.parentLayout)
            image = itemView.findViewById(R.id.image)
            imgDelete = itemView.findViewById(R.id.img_delete)
            tvDescription = itemView.findViewById(R.id.tvDescription)
        }
    }


    override fun onDialogConfirmAction(mView : View?, mKey : String) {
        confirmationDialog?.dismiss()
        imageListActivity.deletePhoto(position)

    }


    override fun onDialogCancelAction(mView : View?, mKey : String) {

        confirmationDialog?.dismiss()


    }
}




