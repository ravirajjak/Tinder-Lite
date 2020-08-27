package com.appturbo.tinder.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.appturbo.tinder.R
import com.appturbo.tinder.repository.`interface`.ApiService
import com.appturbo.tinder.utility.PreferenceManager
import com.appturbo.tinder.utility.Util
import com.google.gson.Gson


open class BaseActivity : AppCompatActivity() {
    private var dialog: Dialog? = null
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

    fun hideProgress() {
        if (dialog != null) {
            if (dialog!!.isShowing()) dialog!!.dismiss()
        }
    }

    fun showProgress() {
        dialog = getProgressDialog()
        dialog!!.show()
    }

    open fun getProgressDialog(): Dialog {
        if (dialog == null) {
            dialog = Dialog(this)
            dialog!!.window?.requestFeature(Window.FEATURE_NO_TITLE)
        }
        dialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCancelable(false)
        val factory = LayoutInflater.from(this)
        val customPopupView: View = factory.inflate(R.layout.dialog_progress, null)
        dialog!!.setContentView(customPopupView)
        return dialog!!
    }

}