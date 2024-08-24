package com.loyltworks.mandelapremium.utils


import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipeableViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Disable touch events
        return false
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        // Disable intercepting touch events
        return false
    }
}
