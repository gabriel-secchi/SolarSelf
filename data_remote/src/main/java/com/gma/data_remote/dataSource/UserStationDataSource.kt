package com.gma.data_remote.dataSource

import com.gma.data_remote.models.StationDetailResultModel
import com.gma.data_remote.models.StationListResultModel

interface UserStationDataSource {
    suspend fun list(params: ParamsToList): StationListResultModel
    suspend fun detail(stationId: String): StationDetailResultModel

    data class ParamsToList(
        val pageNo: Int,
        val pageSize: Int
    )
}