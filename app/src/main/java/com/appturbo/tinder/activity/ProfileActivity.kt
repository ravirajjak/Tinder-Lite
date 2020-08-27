package com.appturbo.tinder.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.appturbo.tinder.R
import com.appturbo.tinder.databinding.ActivityProfileBinding
import com.appturbo.tinder.repository.model.response.Result
import com.appturbo.tinder.utility.AppConstant

class ProfileActivity : BaseActivity() {

    lateinit var binding: ActivityProfileBinding
    var mProfileData: Result? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        getIntentData()
        init()
    }

    private fun getIntentData() {
        mProfileData = intent.getParcelableExtra(AppConstant.PE_PROFILE_DATA)
    }

    private fun init() {
        if (mProfileData != null)
            mUtility.loadImage(this, binding.actProfileImgDisplay, mProfileData!!.user.picture)
        binding.actProfileIncScrolling.result = mProfileData
        binding.actProfileIncScrolling.utility = mUtility
        setOnClickListener()

    }

    private fun setOnClickListener() {
        binding.actProfileImgBack.setOnClickListener {
            finish()
        }
    }
}