package com.gma.data_remote.models

data class StationDataList(
    val stationStatusVo: StationStatusVo,
    val page: StationModel,
    val mpptSwitch: Int
)
