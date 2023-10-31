package com.gma.data_remote.models

data class StationMonthResultModel(
    val success: Boolean,
    val code: String,
    val msg: String,
    val data: List<StationMonthDataResultModel>
)
