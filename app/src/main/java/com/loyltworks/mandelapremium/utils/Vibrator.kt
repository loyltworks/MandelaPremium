package com.loyltworks.mandelapremium.utils

import android.app.Activity
import android.content.Context
import android.os.Vibrator

object Vibrator {

    fun vibrate(activity: Activity){
        val vibe = activity.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibe.vibrate(50)
    }

}