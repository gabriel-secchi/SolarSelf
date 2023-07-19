package com.gma.solarself.inputValidators

import android.content.Context

abstract class InvalidInputException: Exception() {
    abstract fun getReason(context: Context): String
}