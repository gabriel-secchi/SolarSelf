package com.gma.data_remote.models

data class StationStatusVo(
    val all: Int,
    val normal: Int,
    val fault: Int,
    val offline: Int,
    val building: Int,
    val mppt: Int,
)
