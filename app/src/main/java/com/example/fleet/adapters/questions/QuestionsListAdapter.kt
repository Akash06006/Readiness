package com.uniongoods.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fleet.R
import com.example.fleet.views.home.HomeFragment
import com.example.fleet.databinding.QuestionItemBinding

class QuestionsListAdapter(
    context : HomeFragment,
    addressList : ArrayList<String>?
) :
    RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {
    private val mContext : HomeFragment
    private var viewHolder : ViewHolder? = null
    private var addressList : ArrayList<String>?

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
        /* holder.binding!!.tvAddress.text = addressList[position].addressType
         holder.binding!!.tvAddressDetail.text = addressList[position].addressName
         holder.binding.rdDefault.setOnClickListener {

             holder.binding.rdDefault.setChecked(false)

         }*/
    }

    override fun getItemCount() : Int {
        return 10// addressList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
        (
        v : View, val viewType : Int, //These are the general elements in the RecyclerView
        val binding : QuestionItemBinding?,
        mContext : HomeFragment,
        addressList : ArrayList<String>?
    ) : RecyclerView.ViewHolder(v) {
        /*init {
            binding.linAddress.setOnClickListener {
                mContext.deleteAddress(adapterPosition)
            }
        }*/
    }
}