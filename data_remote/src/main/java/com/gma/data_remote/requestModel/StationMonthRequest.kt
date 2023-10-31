package com.gma.data_remote.requestModel

data class StationMonthRequest(
    val id: String,
    val month: String,
    val nmiCode: String? = ""
)
