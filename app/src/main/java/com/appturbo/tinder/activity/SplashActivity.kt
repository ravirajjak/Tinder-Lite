package com.appturbo.tinder.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.appturbo.tinder.activity.BaseActivity.Companion.newInstance
import com.appturbo.tinder.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_splash)
        openIntent(newInstance(HomeActivity::class.java))

    }


    private fun openIntent(mClass: Class<*>) {
        lifecycleScope.launch {
            delay(3000L)
            val intent = Intent(this@SplashActivity, newInstance(mClass))
            startActivity(intent)
            finish()
        }
    }
}