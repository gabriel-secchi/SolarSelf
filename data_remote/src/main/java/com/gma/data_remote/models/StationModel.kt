package com.gma.data_remote.models

data class StationModel(
    val records: List<StationRecord>,
    val total: Int,
    val size: Int,
    val current: Int,
    val pages: Int,
)
