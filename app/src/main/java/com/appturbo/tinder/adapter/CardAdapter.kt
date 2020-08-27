package com.appturbo.tinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.appturbo.tinder.R
import com.appturbo.tinder.databinding.LayoutItemCardBinding
import com.appturbo.tinder.repository.model.response.Name
import com.appturbo.tinder.repository.model.response.Result
import com.appturbo.tinder.utility.Util

class CardAdapter(val context: Context) : RecyclerView.Adapter<CardAdapter.MyViewHolder>() {

    lateinit var mDataList: ArrayList<Result>
    val mUtil by lazy {
        Util()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = DataBindingUtil.inflate<LayoutItemCardBinding>(
            layoutInflater,
            R.layout.layout_item_card,
            parent,
            false
        )
        return MyViewHolder(binding)

    }

    fun setData(mDataList: List<Result>) {
        this.mDataList = mDataList as ArrayList<Result>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (!::mDataList.isInitialized)
            mDataList = ArrayList()
        return mDataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mResult = mDataList.get(position)
        holder.binding.result = mResult

        if (mResult.user.picture.isNotEmpty()) {
            mUtil.loadCircularImage(
                context,
                holder.binding.layItemCardImgDisplay,
                mResult.user.picture
            )
        }

        setInitialSelection(holder,mResult)

        setOnClickListener(holder, mResult)
    }

    private fun setInitialSelection(holder: MyViewHolder, mResult: Result) {
        holder.binding.result?.userInfo = mUtil.getFirstAndLast(mResult.user.name)
        holder.binding.result?.userHeader = "Name"
        setTint(holder.binding.layItemCardImgName)
        setTintNull(holder.binding.layItemCardImgDob)
        setTintNull(holder.binding.layItemCardImgLocation)
        setTintNull(holder.binding.layItemCardImgCall)
        setTintNull(holder.binding.layItemCardImgLock)
    }

    private fun setOnClickListener(
        holder: MyViewHolder, mResult: Result
    ) {


        holder.binding.layItemCardImgName.setOnClickListener {
            holder.binding.result?.userInfo = mUtil.getFirstAndLast(mResult.user.name)
            holder.binding.result?.userHeader = "Name"
            setTint(holder.binding.layItemCardImgName)
            setTintNull(holder.binding.layItemCardImgDob)
            setTintNull(holder.binding.layItemCardImgLocation)
            setTintNull(holder.binding.layItemCardImgCall)
            setTintNull(holder.binding.layItemCardImgLock)
        }
        holder.binding.layItemCardImgDob.setOnClickListener {
            setTint(holder.binding.layItemCardImgDob)
            holder.binding.result?.userHeader = "DOB"

            holder.binding.result?.userInfo = mResult.user.dob
            setTintNull(holder.binding.layItemCardImgName)
            setTintNull(holder.binding.layItemCardImgLocation)
            setTintNull(holder.binding.layItemCardImgCall)
            setTintNull(holder.binding.layItemCardImgLock)
        }
        holder.binding.layItemCardImgLocation.setOnClickListener {
            holder.binding.result?.userInfo = mUtil.getLocation(mResult.user.location)
            holder.binding.result?.userHeader = "Address"

            setTint(holder.binding.layItemCardImgLocation)
            setTintNull(holder.binding.layItemCardImgName)
            setTintNull(holder.binding.layItemCardImgDob)
            setTintNull(holder.binding.layItemCardImgCall)
            setTintNull(holder.binding.layItemCardImgLock)
        }
        holder.binding.layItemCardImgCall.setOnClickListener {
            holder.binding.result?.userInfo = mResult.user.cell
            holder.binding.result?.userHeader = "Mobile Number"

            setTint(holder.binding.layItemCardImgCall)
            setTintNull(holder.binding.layItemCardImgName)
            setTintNull(holder.binding.layItemCardImgDob)
            setTintNull(holder.binding.layItemCardImgLocation)
            setTintNull(holder.binding.layItemCardImgLock)
        }
        holder.binding.layItemCardImgLock.setOnClickListener {
            holder.binding.result?.userInfo = mResult.user.registered
            holder.binding.result?.userHeader = "Register Id"

            setTint(holder.binding.layItemCardImgLock)
            setTintNull(holder.binding.layItemCardImgName)
            setTintNull(holder.binding.layItemCardImgCall)
            setTintNull(holder.binding.layItemCardImgDob)
            setTintNull(holder.binding.layItemCardImgLocation)
        }
    }

    fun setTintNull(imgView: ImageView) {
        imgView.setColorFilter(null)
    }

    fun setTint(imgView: ImageView) {
        imgView.setColorFilter(
            ContextCompat.getColor(
                context,
                R.color.colorAccent
            ), android.graphics.PorterDuff.Mode.SRC_IN
        )
    }


    class MyViewHolder(val binding: LayoutItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}