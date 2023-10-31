package com.gma.solarself.utils

fun Double.twoDecimalPlaces(): String {
    return String.format(TWO_DECIMAL_PLACES, this)
}

private const val TWO_DECIMAL_PLACES =  "%.2f"