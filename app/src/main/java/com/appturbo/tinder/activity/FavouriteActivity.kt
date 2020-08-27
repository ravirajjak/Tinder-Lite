package com.appturbo.tinder.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.appturbo.tinder.R
import com.appturbo.tinder.adapter.CardAdapter
import com.appturbo.tinder.databinding.ActivityMainBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager

class FavouriteActivity : BaseActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {

        setTitle()
        hideHeartIcon()
        setBackIconOnHamburger()
        setOnClickListener()
        setAdapter()


    }

    private fun setOnClickListener() {
        binding.incAppHeader.incAhHamburger.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter() {
        val mDataList = mUtility.getFavouriteProfiles()
        binding.actMainCardstackview.layoutManager = CardStackLayoutManager(this).apply {
            setVisibleCount(2)
        }
        val mAdapter = CardAdapter(this)
        binding.actMainCardstackview.adapter = mAdapter
        mDataList?.let { mAdapter.setData(it) }

    }

    private fun hideHeartIcon() {
        binding.incAppHeader.incAhFavourite.visibility = View.GONE

    }

    private fun setTitle() {
        binding.incAppHeader.incAhTitle.setText(getString(R.string.lbl_my_favourite))
    }

    private fun setBackIconOnHamburger() {
        binding.incAppHeader.incAhHamburger.setImageResource(R.drawable.ic_arrow_left)
    }


}