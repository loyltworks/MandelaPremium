package com.loyltworks.mandelapremium.utils

import android.content.Context
import android.text.format.DateUtils
import com.loyltworks.rootchecker.rootChecker.RootChecker

object AppController {


    // mm/dd//yyyy to dd/mm/yyyy
    fun dateFormatddmmyy(reqDate: String): String? {
        val day = reqDate.split("/".toRegex()).toTypedArray()[1]
        val month = reqDate.split("/".toRegex()).toTypedArray()[0]
        val year = reqDate.split("/".toRegex()).toTypedArray()[2]
        return "$day/$month/$year"
    }

    fun dateFormat(reqDate: String): String? {
        val year = reqDate.split("-".toRegex()).toTypedArray()[0]
        val day = reqDate.split("-".toRegex()).toTypedArray()[2]
        val month = reqDate.split("-".toRegex()).toTypedArray()[1]
        return "$day/$month/$year"
    }

    fun dateUIFormat(reqDate: String): String? {
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


    fun appOpenedToday(context: Context): Boolean{
        return DateUtils.isToday(PreferenceHelper.getLongValue(context, "TodayDate"))
    }

    /*** Device is Rooted or Not ***/
    fun deviceIsRootedOrNot(context: Context?):Boolean{
        val rootChecker = RootChecker.isRooted(context)
        return rootChecker
    }
}