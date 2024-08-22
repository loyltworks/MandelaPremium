package com.loyltworks.mandelapremium.ui.SplashScreen

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.ui.Login.LoginActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashboardActivity
import com.loyltworks.mandelapremium.utils.PreferenceHelper

class SplashScreenActivity : AppCompatActivity() {


    private lateinit var splashScreenViewModel: SplashScreenViewModel

    private var PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.loyltworks.mandelapremium&hl=en"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashScreenViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)

        val packageInfo: PackageInfo =  packageManager!!.getPackageInfo(packageName, 0)

        splashScreenViewModel.checkIfUpdateAvailabe(this, packageInfo.versionCode)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            when {
                PreferenceHelper.getBooleanValue(this, BuildConfig.IsLoggedIn) -> {
                    val intent = Intent(this, DashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                else -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            finish()
        }, 2000)



        /*   splashScreenViewModel.get_isUpdateAvailable()!!.observe(this, Observer {

               if (it) {
                   val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_LINK))
                   startActivity(browserIntent)
               } else {

                   Handler(Looper.getMainLooper()).postDelayed(Runnable {
                       when {
                           PreferenceHelper.getBooleanValue(this, BuildConfig.IsLoggedIn) -> {
                               val intent = Intent(this, DashboardActivity::class.java)
                               intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                               startActivity(intent)
                           }
                           else -> {
                               startActivity(Intent(this, LoginActivity::class.java))
                           }
                       }
                       finish()
                   }, 2000)


               }
           })*/




    }
}