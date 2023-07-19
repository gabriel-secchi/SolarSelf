package com.gma.data_remote.models

data class StationDetailResultModel(
    val success: Boolean?,
    val code: String?,
    val msg: String?,
    val data: StationRecord?,
    val status: Int?,
    val error: String?,
    val message: String?
)
