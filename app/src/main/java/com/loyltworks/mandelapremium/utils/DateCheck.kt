package com.loyltworks.mandelapremium.utils

import android.util.Log
import java.util.*

object DateCheck {

    // date format : dd/mm/yyyy
    fun isToday(date: String): Int {
        val c: Calendar = Calendar.getInstance()

// set the calendar to start of today

// set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

// and get that as a Date

// and get that as a Date
        val today: Date = c.time

// or as a timestamp in milliseconds

// or as a timestamp in milliseconds
        val todayInMillis: Long = c.timeInMillis

// user-specified date which you are testing
// let's say the components come from a form or something

// user-specified date which you are testing
// let's say the components come from a form or something
        val year = date.split("/")[2] as Int
        val month = date.split("/")[1] as Int
        val dayOfMonth = date.split("/")[0] as Int

// reuse the calendar to set user specified date

// reuse the calendar to set user specified date
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

// and get that as a Date

// and get that as a Date
        val dateSpecified: Date = c.time

// test your condition

// test your condition
        return when {
            dateSpecified.before(today) -> {
                -1
            } // yesterday
            dateSpecified.after(today) -> {
                1
            } // tomorrow
            else -> {
                0
            } // today
        }
        /*  if (dateSpecified.before(today)) {
              Log.d("DateCheck", "Date specified [$dateSpecified] is before today [$today]")

          } else {
              Log.d("DateCheck", "Date specified [$dateSpecified] is NOT before today [$today]")
          }*/
    }
}