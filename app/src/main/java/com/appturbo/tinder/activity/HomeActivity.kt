package com.appturbo.tinder.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.appturbo.tinder.R
import com.appturbo.tinder.adapter.CardAdapter
import com.appturbo.tinder.databinding.ActivityMainBinding
import com.appturbo.tinder.repository.model.response.Result
import com.appturbo.tinder.utility.AppConstant
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity(), CardStackListener {

    val TAG = HomeActivity::class.java.simpleName
    lateinit var binding: ActivityMainBinding
    lateinit var mDataList: List<Result>
    lateinit var mResult: Result


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        setOnClickListener()
        setOnSwipeRefreshListener()
        getData()
    }

    private fun setOnSwipeRefreshListener() {
        binding.actMainSwiperefresh.setOnRefreshListener {
            getData()
            binding.actMainSwiperefresh.isRefreshing = false
        }
    }

    private fun setOnClickListener() {
        binding.incAppHeader.incAhFavourite.setOnClickListener {
            openIntent(newInstance(MainActivity::class.java))
        }
    }

    private fun getData() {

        lifecycleScope.launch(Dispatchers.IO) {
            val mResponse = mApiService.getHomeData()
            if (mResponse.isSuccessful) {
                lifecycleScope.launch(Dispatchers.Main) {
                    val mHomePResponse = mResponse.body()
                    onSuccess(mHomePResponse?.results)
                }
            } else {
                Log.d(TAG, "Failure")
            }
        }
    }

    private fun onSuccess(mDataList: List<Result>?) {

        binding.actMainCardstackview.layoutManager = CardStackLayoutManager(this, this)
        val mAdapter = CardAdapter(this)
        binding.actMainCardstackview.adapter = mAdapter
        mDataList?.let { mAdapter.setData(it) }
        if (mDataList != null) {
            this.mDataList = mDataList
        }

    }

    override fun onCardDisappeared(view: View?, position: Int) {
        view?.setOnClickListener(null)
        mResult = mDataList.get(position)
        Thread.sleep(300)
//        Toast.makeText(this, "Position " + position, Toast.LENGTH_SHORT).show()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
        Log.d("Direction ", direction.toString() + "  " + ratio)
    }

    override fun onCardSwiped(direction: Direction?) {

        when (direction?.ordinal) {
            0 -> {
                openIntent(newInstance(ProfileActivity::class.java))
            }
            1 -> {
                mUtility.setFavouriteProfiles(mResult.user.registered.toInt(), gson.toJson(mResult))
            }
        }
//        Toast.makeText(this, "Direc " + direction?.ordinal, Toast.LENGTH_SHORT).show()
    }

    private fun openIntent(mClass: Class<*>) {
        val intent = Intent(this, mClass)
        if (::mResult.isInitialized)
            intent.putExtra(AppConstant.PE_PROFILE_DATA, mResult)
        startActivity(intent)
    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        view?.setOnClickListener(null)
    }

    override fun onCardRewound() {
        Log.d("Direction ", "OnCardRewound")
    }

}