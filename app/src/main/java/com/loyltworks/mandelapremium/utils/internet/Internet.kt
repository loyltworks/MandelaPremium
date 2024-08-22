package com.oneloyalty.goodpack.utils.internet

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.loyltworks.mandelapremium.ApplicationClass

object Internet {

     @RequiresApi(Build.VERSION_CODES.M)
     fun isNetworkConnected(): Boolean {
        //1
        val connectivityManager = ApplicationClass.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //2
        val activeNetwork = connectivityManager.activeNetwork
        //3
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        //4
        return networkCapabilities != null &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}