package com.loyltworks.mandelapremium.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.*

object LocationAddress {
    fun getAddress(context: Context, lat: Double, lng: Double): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        var add = ""
        try {
            val addresses = geocoder.getFromLocation(lat, lng, 1)
            val obj = addresses[0]
            add = obj.getAddressLine(0)
            add = add + ":" + obj.getCountryName();
            add = add + ":" + obj.getCountryCode();
            add = add + ":" + obj.getAdminArea();
            add = add + ":" + obj.getPostalCode();
            add = add + ":" + obj.getSubAdminArea();
            add = add + ":" + obj.getLocality();
            add = add + ":" + obj.getSubThoroughfare();
            Log.v("IGA", "Address$add")

        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
        return add
    }
}