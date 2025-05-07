package com.gma.data_remote.models

data class StationListResultModel(
    val success: Boolean?,
    val code: String?,
    val msg: String?,
    val stationData: StationDataList?,
    val status: Int?,
    val error: String?,
    val message: String?
)
