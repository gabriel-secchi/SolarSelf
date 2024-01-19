package com.gma.solarself.utils

import android.content.Context
import androidx.core.content.ContextCompat

fun getColorInRGB(context: Context, colorResourceId: Int): String {
    val colorValue = ContextCompat.getColor(context, colorResourceId)
    val hexColor = String.format("#%06X", 0xFFFFFF and colorValue)
    return hexColor
}