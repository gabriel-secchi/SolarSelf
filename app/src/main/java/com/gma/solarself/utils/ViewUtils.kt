package com.gma.solarself.utils

import android.view.View

fun View.setSafeOnClickListener(interval: Long = 1000L, onSafeClick: (View) -> Unit) {
    var lastClickTime = 0L
    setOnClickListener {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime > interval) {
            lastClickTime = currentTime
            onSafeClick(it)
        }
    }
}