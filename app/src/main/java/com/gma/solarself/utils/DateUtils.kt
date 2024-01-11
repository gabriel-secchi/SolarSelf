package com.gma.solarself.utils

import java.util.Calendar
import java.util.Date

fun Date.toCalendar(): Calendar {
    return Calendar.getInstance().apply { time = this@toCalendar }
}

fun Date.currentDay(): Int {
    return this.toCalendar().get(Calendar.DAY_OF_MONTH)
}

fun Date.currentDayAndMonth(): String {
    val day = fixToText(this.currentDay())
    val month = fixToText(this.currentMonth())
    return "$day/$month"
}

private fun fixToText(value: Int): String {
    return if (value < 10) "0$value" else value.toString()
}

fun Date.currentMonth(): Int {
    return this.toCalendar().get(Calendar.MONTH) + 1
}

fun Date.currenMonthAndYear(): String {
    val month = fixToText(this.currentMonth())
    return "$month/${this.currentYear()}"
}

fun Date.monthOfYear(): Int {
    return this.toCalendar().get(Calendar.MONTH)
}

fun Date.currentYear(): Int {
    return this.toCalendar().get(Calendar.YEAR)
}

fun Date.sumDays(days: Int): Date {
    return this.toCalendar().addDays(days).time
}

fun Date.subtractDays(days: Int): Date {
    return if (days > 0)
        this.toCalendar().addDays(days * -1).time
    else
        this.toCalendar().addDays(days).time
}

private fun Calendar.addDays(days: Int): Calendar {
    this.apply {
        add(Calendar.DAY_OF_MONTH, days)
        return this
    }
}

fun buildDateDisplay(year: Int, month: Int, day: Int): String {
    val dayToDisplay = fixToText(day)
    val monthToDisplay = fixToText(month + 1)
    return "$dayToDisplay/$monthToDisplay/$year"
}

fun Date?.toStringFormat(): String {
    if (this == null)
        return ""

    return buildDateDisplay(
        this.currentYear(),
        this.monthOfYear(),
        this.currentDay()
    )
}

fun String.toDate(): Date? {
    if (this.isBlank())
        return null

    return try {
        val dateParts = this.split("/")
        val date = Calendar.getInstance().apply {
            set(
                dateParts[2].toInt(),
                dateParts[1].toInt() - 1,
                dateParts[0].toInt()
            )
        }
        date.time
    } catch (ex: Exception) {
        ex.printStackTrace()
        null
    }
}