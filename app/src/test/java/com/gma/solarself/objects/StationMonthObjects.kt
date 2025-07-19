package com.gma.solarself.objects

import com.gma.infrastructure.model.StationMonthDataModel
import com.gma.solarself.utils.currentMonth
import com.gma.solarself.utils.currentYear
import java.util.Calendar
import java.util.Date

fun getStationMonthDate(): Date {
    calendar.set(2025, Calendar.JULY, 1)
    return calendar.time
}

fun getCurrentMontAndYear(): String {
    val month = currentDate.currentMonth()
    val year = currentDate.currentYear()
    return "$year-$month"
}

val currentDate = Date()

val stationMonthDataList = listOf(
    StationMonthDataModel(
        id = selectedStationId,
        money = 10.9F,
        energy = 1.3F,
        energyStr = "1,3",
        date = getStationMonthDate()
    ),
    StationMonthDataModel(
        id = selectedStationId,
        money = 20.9F,
        energy = 2.3F,
        energyStr = "2,3",
        date = getStationMonthDate()
    )
)