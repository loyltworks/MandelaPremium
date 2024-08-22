package com.loyltworks.mandelapremium.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


object DatePickerBox {

    fun date(activity: Activity?, dates: (date: String) -> Unit) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            activity!!, AlertDialog.THEME_DEVICE_DEFAULT_DARK,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                var dayOfMonths = "$dayOfMonth"
                var monthOfYears = "${monthOfYear + 1}"

                if (dayOfMonth < 10) dayOfMonths = "0$dayOfMonth"
                if (monthOfYear + 1 < 10) monthOfYears = "0${monthOfYear + 1}"

                dates("$dayOfMonths/${monthOfYears}/$year")
            },
            year,
            month,
            day
        )

        dpd.datePicker.maxDate = Date().time


        dpd.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun dateCompare(
        context: Context?,
        fromDate: String,
        toDate: String,
        Val: (status: Boolean) -> Unit
    ) {

        if (fromDate == null || fromDate == "") {
            Val(true)
            return
        }

        if (toDate == null || toDate == "") {
            Val(true)
            return
        }

        val datecompare = SimpleDateFormat("dd/MM/yyyy")

        when {
            datecompare.parse(fromDate).after(datecompare.parse(toDate)) -> {
                Toast.makeText(context, "From date should smaller than To date", Toast.LENGTH_SHORT)
                    .show()
                Val(false)
            }
            datecompare.parse(toDate).before(datecompare.parse(fromDate)) -> {
                Toast.makeText(context, "To date should greater than From date", Toast.LENGTH_SHORT)
                    .show()
                Val(false)
            }
            else -> Val(true)
        }
    }

}