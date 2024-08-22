package com.loyltworks.mandelapremium.utils.internet

interface ConnectivityReceiverListener {
    fun onNetworkConnectionChanged(isConnected: Boolean)
}