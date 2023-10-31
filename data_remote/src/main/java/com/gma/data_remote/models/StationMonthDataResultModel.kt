package com.gma.data_remote.models

data class StationMonthDataResultModel(
    val id: String,
    val money: Float,
    val energy: Float,
    val energyStr: String,
    val date: Long
)
