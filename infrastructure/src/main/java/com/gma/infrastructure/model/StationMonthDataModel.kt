package com.gma.infrastructure.model

import java.util.Date

data class StationMonthDataModel(
    val id: String,
    val money: Float,
    val energy: Float,
    val energyStr: String,
    val date: Date
)
