package com.gma.data_remote.dataSource

import com.gma.data_remote.models.StationMonthResultModel

interface StationMonthDataSource {
    suspend fun list(params: ParamsToList): StationMonthResultModel

    data class ParamsToList(
        val id: String,
        val month: String
    )
}