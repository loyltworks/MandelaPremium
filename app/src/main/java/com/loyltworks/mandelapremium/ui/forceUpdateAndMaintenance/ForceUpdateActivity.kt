package com.loyltworks.mandelapremium.ui.forceUpdateAndMaintenance

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.databinding.ActivityForceUpdateBinding


class ForceUpdateActivity : AppCompatActivity() {
    lateinit var binding : ActivityForceUpdateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForceUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*** Firebase Analytics Tracker ***/
        val bundle1 = Bundle()
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "ForceUpadateView")
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "ForceUpdateActivity")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1)


        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding.forceupdate.setOnClickListener { v: View? ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PLAY_STORE_LINK))
            startActivity(browserIntent)
            finish()
        }

    }
}