package com.netcore.android.inapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import android.widget.PopupWindow
import androidx.annotation.VisibleForTesting
import com.netcore.android.SMTEventParamKeys
import com.netcore.android.Smartech
import com.netcore.android.db.SMTDataBaseService
import com.netcore.android.event.SMTEventId
import com.netcore.android.event.SMTEventRecorder
import com.netcore.android.event.SMTEventType
import com.netcore.android.inapp.model.SMTInAppRule
import com.netcore.android.logger.SMTLogger
import com.netcore.android.network.models.SMTSdkInitializeResponse
import com.netcore.android.preference.SMTPreferenceConstants
import com.netcore.android.preference.SMTPreferenceHelper
import com.netcore.android.utility.SMTCommonUtility
import org.json.JSONObject
import java.lang.ref.WeakReference
import java.net.URL
import java.util.*


@Suppress("PrivatePropertyName")
/**
 * @author Netcore
 * created on 8/03/2019
 * @Description: Class which handles the InApp rule display
 *
 */
internal class SMTInAppHandler private constructor() : SMTInAppRuleActionListener {


    private val TAG = SMTInAppHandler::class.java.simpleName

    private var mPopupWindow: PopupWindow? = null

    private var isRecordInAppDismissEent = true

    companion object {
        @Volatile
        private var instance: SMTInAppHandler? = null

        @Synchronized
        fun getInstance(): SMTInAppHandler =
                SMTInAppHandler.instance ?: synchronized(SMTInAppHandler::class.java) {
                    SMTInAppHandler.instance
                            ?: SMTInAppHandler.buildInstance().also { SMTInAppHandler.instance = it }
                }


        private fun buildInstance(): SMTInAppHandler {
            return SMTInAppHandler()
        }
    }

    /**
     * Check the event payload against the applicable inapp rule
     * @param payloadMap payload of a event. System or custom event
     */
    fun checkRule(payloadMap: HashMap<String, Any>) {

        if (isInAppOptIn()) {
            /**
             * If activity is in foreground then show the in app notifications
             */
            if (SMTInAppUtility.isAppInForeground()) {
                val activity: Activity? = SMTInAppUtility.getForeGroundActivity() ?:
                /**
                 * current foreground activity is null. so returning here
                 */
                return

                val inAppRules: MutableList<SMTInAppRule>? = getInAppRules(payloadMap, activity!!)
                val identifiedRule = findARule(activity, inAppRules, payloadMap)
                if (identifiedRule != null) {
                    if (SMTCommonUtility.isNetworkAvailable(activity)) {
                        showInAppRule(identifiedRule)
                    } else {
                        SMTLogger.internal(TAG, "Network is not available.")
                    }
                }


            } else {
                SMTLogger.e(TAG, "Application isn't in foreground so rejecting the InAPP request")
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun isInAppOptIn(): Boolean {
        SMTInAppUtility.getForeGroundActivity()?.let {
            return SMTPreferenceHelper.getAppPreferenceInstance(it.applicationContext, null).getBoolean(SMTPreferenceConstants.OPT_IN_OUT_IN_APP_MESSAGES, true)
        }

        return false
    }

    /**
     * Get all the matching in app rules for the given event and for the current time
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getInAppRules(eventPayLoad: HashMap<String, Any>, activity: Activity): MutableList<SMTInAppRule>? {
        return SMTDataBaseService.getInstance(WeakReference(activity.applicationContext)).getInAppRules(eventPayLoad)
    }

    /**
     * Find a rule which matches the criteria considering the filters and usage
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findARule(context: Context, inAppRules: MutableList<SMTInAppRule>?, eventPayLoad: HashMap<String, Any>): SMTInAppRule? {

        var matchedRule: SMTInAppRule? = null
        if (inAppRules != null && inAppRules.size > 0) {
            val listIterator = inAppRules.listIterator()
            while (listIterator.hasNext()) {
                val inAppRule = listIterator.next()
                var isShowRule = false

                // Save MID, CG and RandomValue in pref so we can send
                // these value in other events
                SMTPreferenceHelper.getAppPreferenceInstance(context, null).setString(SMTPreferenceConstants.SMT_MID, inAppRule.id)
                SMTPreferenceHelper.getAppPreferenceInstance(context, null).setString(SMTPreferenceConstants.SMT_CG, if (inAppRule.controlGroup > inAppRule.randomNumber) "1" else "0")
                SMTPrefere