package com.loyltworks.mandelapremium.utils.dialogBox

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.loyltworks.mandelapremium.R

object TCDialog {


    var mDialogTC: Dialog? = null

    interface TCCallback {
        fun onAcceptAction(mDialogs: Dialog?)
        fun onDeclineAction(mDialogs: Dialog?)
    }

    fun getTCAlert(context: Context?, tcCallback: TCCallback) {
        if (context == null) return
        if (mDialogTC != null) return
        mDialogTC = Dialog(context)
        mDialogTC!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialogTC!!.setContentView(R.layout.tc_alert_layout)
        mDialogTC!!.setCanceledOnTouchOutside(false)
        mDialogTC!!.setCancelable(false)
        val window: Window? = mDialogTC!!.window
        window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val mDecline: AppCompatButton = mDialogTC!!.findViewById(R.id.decline_btn)
        val mAccept_btn: AppCompatButton = mDialogTC!!.findViewById(R.id.accept_btn)
        val mWebHost: WebView = mDialogTC!!.findViewById<WebView>(R.id.feature_view)
        val mAcceptDecline: LinearLayout =
            mDialogTC!!.findViewById<LinearLayout>(R.id.accept_decline_ll)
        mAcceptDecline.visibility = View.VISIBLE
        val webSetting = mWebHost.settings
        webSetting.builtInZoomControls = false
        webSetting.javaScriptEnabled = true
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        val res = context.resources
        val fontSize = res.getDimension(R.dimen.text).toInt()
        webSetting.defaultFontSize = fontSize
        mWebHost.webViewClient = WebViewClient()
        mWebHost.loadUrl("file:///android_asset/mandela_tc.html")
        val layoutParams: WindowManager.LayoutParams = mDialogTC!!.window!!.attributes
        layoutParams.windowAnimations = R.style.DialogAnimation
        mDecline.setOnClickListener {
            tcCallback.onDeclineAction(mDialogTC)
            mDialogTC!!.dismiss()
            mDialogTC = null
        }
        mAccept_btn.setOnClickListener {
            tcCallback.onAcceptAction(mDialogTC)
            mDialogTC!!.dismiss()
            mDialogTC = null
        }

        mDialogTC!!.show()

    }
}