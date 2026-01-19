package com.loyltworks.mandelapremium.ui.SplashScreen

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyltworks.mandelapremium.BuildConfig
import com.loyltworks.mandelapremium.R
import com.loyltworks.mandelapremium.ui.Login.LoginActivity
import com.loyltworks.mandelapremium.ui.dashboard.DashboardActivity
import com.loyltworks.mandelapremium.utils.AppController
import com.loyltworks.mandelapremium.utils.PreferenceHelper
import com.loyltworks.mandelapremium.utils.dialogBox.RootCheckDialog
import com.loyltworks.mandelapremium.utils.fetchData.ShaKeyManager

class SplashScreenActivity : AppCompatActivity() {


    private lateinit var splashScreenViewModel: SplashScreenViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashScreenViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)

        ShaKeyManager.fetchShaKeys(
            onSuccess = {
                callSecureUpdateCheck()
            },
            onFailure = {
                Toast.makeText(this,getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show()
            }
        )

    }

    fun callSecureUpdateCheck(){
        val packageInfo: PackageInfo = packageManager!!.getPackageInfo(packageName, 0)
        splashScreenViewModel.checkIfUpdateAvailabe(this, packageInfo.versionCode)

        splashScreenViewModel.get_isUpdateAvailable().observe(this, Observer {

            if (it) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PLAY_STORE_LINK))
                startActivity(browserIntent)
            } else {
                if (AppController.deviceIsRootedOrNot(this)) {
                    RootCheckDialog.showRootCheckDialog(this,
                        object : RootCheckDialog.RootCheckDialogCallBack {
                            override fun onOk() {
                                finish()
                            }
                        })
                } else {
                    Handler(Looper.getMainLooper()).postDelayed({
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
            }
        })
    }

}