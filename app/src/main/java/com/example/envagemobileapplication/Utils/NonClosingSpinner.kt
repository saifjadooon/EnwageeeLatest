package com.example.envagemobileapplication.Utils

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Spinner


class NonClosingSpinner(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatSpinner(context, attrs) {

    private var lastTouchTime: Long = 0

    override fun performClick(): Boolean {
        lastTouchTime = System.currentTimeMillis()
        return super.performClick()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (!hasFocus) {
            // If the window loses focus, reset the last touch time to prevent immediate closure
            lastTouchTime = 0
        }
        super.onWindowFocusChanged(hasFocus)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            // Check the time between touch up and last touch time to determine if the spinner should close
            val elapsedTime = System.currentTimeMillis() - lastTouchTime
            if (elapsedTime < 300) {
                return true
            }
        }
        return super.onTouchEvent(event)
    }
}