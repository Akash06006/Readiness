package com.uniongoods.adapters

import android.R.attr.button
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.databinding.QuestionItemBinding
import com.example.fleet.model.home.QuestionData
import com.example.fleet.views.home.HomeFragment


class QuestionsListAdapter(
    context : HomeFragment,
    addressList : ArrayList<QuestionData.Data>?
) :
    RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {
    private val mContext : HomeFragment
    private var viewHolder : ViewHolder? = null
    private var addressList : ArrayList<QuestionData.Data>?

    init {
        this.mContext = context
        this.addressList = addressList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent : ViewGroup, viewType : Int) : ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.question_item,
            parent,
            false
        ) as QuestionItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, addressList)
    }

    override fun onBindViewHolder(@NonNull holder : ViewHolder, position : Int) {
        viewHolder = holder
        holder.binding!!.tvQuestion.text = addressList!![position].question
        holder.binding!!.imgYes.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_selected))
        holder.binding!!.imgNo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_selected))
        holder.binding!!.imgNa.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_not_selected))

        if (!TextUtils.isEmpty(addressList!![position].selected)) {
            if (addressList!![position].selected.equals("yes")) {
                // val img : Drawable = mContext.getResources().getDrawable(R.drawable.ic_yes)
                //  img.setBounds(0, 0, 60, 60);
                holder.binding!!.imgYes.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_yes))
            } else if (addressList!![position].selected.equals("no")) {
                // val img : Drawable = mContext.getResources().getDrawable(R.drawable.ic_no)
                // img.setBounds(0, 0, 60, 60);
                holder.binding!!.imgNo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_no))
            } else if (addressList!![position].selected.equals("na")) {
                //val img : Drawable = mContext.getResources().getDrawable(R.drawable.ic_na)
                //img.setBounds(0, 0, 60, 60);
                holder.binding!!.imgNa.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_na))
            }
        }
        holder.binding!!.tvYes.setOnClickListener {
            mContext.radioClick("yes", addressList!![position].id!!)
        }
        holder.binding!!.tvNo.setOnClickListener {
            mContext.radioClick("no", addressList!![position].id!!)
        }
        holder.binding!!.tvNa.setOnClickListener {
            mContext.radioClick("na", addressList!![position].id!!)
        }
    }

    override fun getItemCount() : Int {
        return addressList!!.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v : View, val viewType : Int, //These are the general elements in the RecyclerView
        val binding : QuestionItemBinding?,
        mContext : HomeFragment,
        addressList : ArrayList<QuestionData.Data>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}