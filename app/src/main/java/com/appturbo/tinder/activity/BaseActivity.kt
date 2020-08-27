package com.appturbo.tinder.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appturbo.tinder.repository.`interface`.ApiService
import com.appturbo.tinder.R
import com.appturbo.tinder.utility.PreferenceManager
import com.appturbo.tinder.utility.Util
import com.google.gson.Gson

open class BaseActivity : AppCompatActivity() {
    val mApiService by lazy {
        ApiService.create()
    }
    val mUtility by lazy {
        Util()
    }
    val mPref by lazy {
        PreferenceManager()
    }
    val gson by lazy {
        Gson()
    }

    companion object {
        fun newInstance(mClass: Class<*>): Class<*> {
            return mClass
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}