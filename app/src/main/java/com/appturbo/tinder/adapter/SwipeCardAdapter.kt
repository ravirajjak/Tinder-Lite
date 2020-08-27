package com.appturbo.tinder.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.appturbo.tinder.R
import com.appturbo.tinder.repository.model.response.Location
import com.appturbo.tinder.repository.model.response.Result
import com.appturbo.tinder.repository.model.response.User
import com.appturbo.tinder.utility.Util


class SwipeCardAdapter(
    context: Context,
    resource: Int
) : ArrayAdapter<User?>(context, resource) {


    lateinit var mDataList: ArrayList<Result>

    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {
        val mTvCardLocation = contentView?.findViewById(R.id.lay_item_card_tv_address) as TextView
        val mImgDisplay = contentView?.findViewById(R.id.lay_item_card_img_display) as ImageView


        val mResult = mDataList.get(position)

        val mImgUrl = mResult.user.picture
        if (mImgUrl != null) {
            if (mImgUrl.isNotEmpty())
                Util().loadCircularImage(context, mImgDisplay, mImgUrl)
        }


        mTvCardLocation.text = getLocation(mResult.user.location)

        return contentView
    }

    fun setData(mList: List<Result>) {
        mDataList = mList as ArrayList<Result>
        notifyDataSetChanged()
    }

    private fun getLocation(location: Location): CharSequence? {
        return location.street.plus(", ").plus(location.city).plus(", ").plus(location.street)

    }

    /* override fun getView(position: Int, contentView: View?, parent: ViewGroup?): View? {
         val tv_card_number = contentView.findViewById(R.id.) as TextView
         tv_card_number.text = card_list[position]
         return contentView
     }*/

    override fun getCount(): Int {
        if (!::mDataList.isInitialized)
            mDataList = ArrayList()
        return mDataList.size
    }

}