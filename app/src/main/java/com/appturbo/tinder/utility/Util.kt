package com.appturbo.tinder.utility

import android.content.Context
import android.widget.ImageView
import com.appturbo.tinder.repository.model.response.Location
import com.appturbo.tinder.repository.model.response.Name
import com.appturbo.tinder.repository.model.response.Result
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Util {
    fun loadCircularImage(context: Context, mImgView: ImageView, mUrl: String) {
        Glide.with(context).load(mUrl).transform(CircleCrop()).transition(
            DrawableTransitionOptions.withCrossFade()
        ).into(mImgView)
    }

    fun getLocation(location: Location): String {
        return location.street.plus(", ").plus(location.city).plus(", ").plus(location.street)

    }

    fun getFirstAndLast(name: Name): String {
        return name.first.plus(" ").plus(name.last)
    }

    fun loadImage(context: Context, mImgView: ImageView, mUrl: String) {
        Glide.with(context).load(mUrl).transition(
            DrawableTransitionOptions.withCrossFade()
        ).into(mImgView)
    }

    /*
        fun setProfileData(mProfile: Result) {
            val gson = Gson()
            val mPref = PreferenceManager()
            val mStrProfile = gson.toJson(mProfile)
            mPref.set
        }

    */
    fun setFavouriteProfiles(registered: Int, value: String) {
        var mList = HashMap<Int, String>()
        val mPref = PreferenceManager()
        val gson = Gson()
        val mStrPop = mPref.getPrefString(PreferenceKeys.PROFILE_DATA)
        if (mStrPop == null) {
            mList.put(registered, value)
            val mStrList = gson.toJson(mList)
            mPref.setPrefString(PreferenceKeys.PROFILE_DATA, mStrList)

        } else {
            val mPopList = genericType<HashMap<Int, String>>()
            mList = gson.fromJson(mStrPop, mPopList)
            mList.put(registered, value)
            val mStrList = gson.toJson(mList)
            mPref.setPrefString(PreferenceKeys.PROFILE_DATA, mStrList)
        }


    }

    inline fun <reified T> genericType() = object : TypeToken<T>() {}.type


    fun getFavouriteProfiles(): ArrayList<Result> {
        var mList: HashMap<Int, String>
        val mPref = PreferenceManager()
        val gson = Gson()
        val mStrList = mPref.getPrefString(PreferenceKeys.PROFILE_DATA)
        val mPopList = genericType<HashMap<Int, String>>()
        val mDataList = ArrayList<Result>()
        if (mStrList != null) {
            mList = gson.fromJson(mStrList, mPopList)
            mList.forEach({ (key, value) ->
                val mData = gson.fromJson(value, Result::class.java)
                mDataList.add(mData)
            })
        }
        return mDataList
    }

}