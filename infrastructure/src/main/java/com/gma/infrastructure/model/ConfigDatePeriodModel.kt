package com.gma.infrastructure.model

import java.util.Date

data class ConfigDatePeriodModel(
    val startDate: Date,
    val endDate: Date,
    val autoUpdatePeriod: Boolean = false
)
