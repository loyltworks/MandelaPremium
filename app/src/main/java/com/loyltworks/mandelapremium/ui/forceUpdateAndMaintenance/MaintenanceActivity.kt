package com.loyltworks.mandelapremium.ui.forceUpdateAndMaintenance

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.loyltworks.mandelapremium.databinding.ActivityMaintenanceBinding

class MaintenanceActivity : AppCompatActivity() {
    lateinit var binding: ActivityMaintenanceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaintenanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*** Firebase Analytics Tracker ***/
        val bundle1 = Bundle()
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_NAME, "MaintenanceView")
        bundle1.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MaintenanceActivity")
        //  bundle.putString(MyAppAnalyticsConstants.Param.TOPIC, topic)
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle1)


        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        binding.exit.setOnClickListener(View.OnClickListener { v: View? ->
            onBackPressed()
            finish()
        })

    }
}