package com.loyltworks.mandelapremium.ui.programinformation

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.databinding.ActivityProgramInformationBinding
import com.loyltworks.mandelapremium.ui.baseClass.BaseActivity

class ProgramInformationActivity : BaseActivity() {

    lateinit var binding: ActivityProgramInformationBinding

    override fun callInitialServices() {
    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProgramInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set context
        context = this

        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        //supportActionBar!!.setDisplayShowHomeEnabled(true)
        //supportActionBar!!.setDisplayShowTitleEnabled(false)

        val toolbarName = intent.getStringExtra("MyActivity")

        binding.toolbarTitle.text = toolbarName

        @SuppressLint("UseCompatLoadingForDrawables") val upArrow =
            resources.getDrawable(R.drawable.ic_back_arrow)
        //supportActionBar!!.setHomeAsUpIndicator(upArrow)


        val mWebHost: WebView = findViewById<WebView>(R.id.featureView)
        val webSetting = binding.featureView.settings
        webSetting.builtInZoomControls = false
        webSetting.javaScriptEnabled = true
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        val res = context?.resources
        val fontSize = res?.getDimension(R.dimen.text)?.toInt()
        webSetting.defaultFontSize = fontSize!!
        mWebHost.webViewClient = WebViewClient()



        if (toolbarName.equals("Terms and Conditions")) {
            mWebHost.loadUrl("file:///android_asset/mandela_tc.html")
        } else if (toolbarName.equals("My Benefits")) {
            mWebHost.loadUrl("file:///android_asset/mandela_benefits.html")
        } else if (toolbarName.equals("About Mandela Club")) {
            mWebHost.loadUrl("file:///android_asset/about_mandela.html")
        } else if (toolbarName.equals("FAQ")) {
            mWebHost.loadUrl("file:///android_asset/mandela_faq.html")
        }

        binding.back.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }


}