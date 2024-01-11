package com.gma.data_local.model

import java.util.Date

data class PeriodModel(
    val startDate: Date,
    val endDate: Date,
    val autoUpdatePeriod: Boolean = false
)
