package com.loyltworks.mandelapremium

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ApplicationClass : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    companion object {
        lateinit  var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}