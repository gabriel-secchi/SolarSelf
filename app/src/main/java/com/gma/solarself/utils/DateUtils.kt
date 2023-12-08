package com.gma.solarself.utils

import java.util.Calendar
import java.util.Date

fun Date.toCalendar() : Calendar {
    return Calendar.getInstance().apply { time = this@toCalendar }
}

fun Date.currentMonth() : Int {
    return this.toCalendar().get(Calendar.MONTH)  + 1
}

fun Date.currentYear() : Int {
    return this.toCalendar().get(Calendar.YEAR)
}