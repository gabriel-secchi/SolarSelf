package com.gma.solarself.utils

import android.util.Log
import androidx.navigation.NavController

fun NavController.safeNavigationTo(actionId: Int) {
    try {
        if(this.currentDestination?.id != actionId) {
            this.navigate(actionId)
        }
    } catch (ex: Exception) {
        Log.e(NAVIGATION_ERROR, ex.printStackTrace().toString())
        ex.printStackTrace()
    }
}

private const val NAVIGATION_ERROR = "safeNavigationTo_error"