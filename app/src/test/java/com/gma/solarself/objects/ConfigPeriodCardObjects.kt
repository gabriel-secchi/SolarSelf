package com.gma.solarself.objects

import com.gma.infrastructure.model.ConfigDatePeriodModel
import java.util.Calendar
import java.util.Date

val calendar = Calendar.getInstance()
fun getInitialDate(): Date {
    calendar.set(2025, Calendar.JULY, 1)
    return calendar.time
}
fun getFinalDate(): Date {
    calendar.set(2025, Calendar.JULY, 20)
    return calendar.time
}

val periodConfiguredAutoUpdated = ConfigDatePeriodModel(
    startDate = getInitialDate(),
    endDate = getFinalDate(),
    autoUpdatePeriod = true
)

val periodConfigured = periodConfiguredAutoUpdated.copy(
    autoUpdatePeriod = false
)