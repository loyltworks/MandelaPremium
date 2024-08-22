package com.loyltworks.mandelapremium.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.*

object DateFormat {

    fun dateFormat(reqDate: String): String? {
        val year = reqDate.split("/".toRegex()).toTypedArray()[2]
        val day = reqDate.split("/".toRegex()).toTypedArray()[1]
        val month = reqDate.split("/".toRegex()).toTypedArray()[0]
        return "$year/$month/$day"
    }

    fun dateUIFormat(reqDate: String): String? {
        val year = reqDate.split("/".toRegex()).toTypedArray()[2]
        val day = reqDate.split("/".toRegex()).toTypedArray()[1]
        val month = reqDate.split("/".toRegex()).toTypedArray()[0]
        return "$day/$month/$year"
    }

    fun dateDOBUIFormat(reqDate: String): String? {
        val year = reqDate.split("/".toRegex()).toTypedArray()[2]
        val day = reqDate.split("/".toRegex()).toTypedArray()[1]
        val month = reqDate.split("/".toRegex()).toTypedArray()[0]
        return "$day/$month/$year"
    }



    fun dateAPIFormats(reqDate: String?): String? {
        if (reqDate != null && !reqDate.isEmpty()) {
            val dateFor: String = reqDate
            return dateFor.split("/".toRegex())
                .toTypedArray()[2] + "-" + dateFor.split("/".toRegex())
                .toTypedArray()[1] + "-" + dateFor.split("/".toRegex()).toTypedArray()[0]
        }
        return reqDate
    }


}