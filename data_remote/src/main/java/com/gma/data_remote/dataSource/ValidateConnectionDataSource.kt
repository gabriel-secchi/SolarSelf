package com.gma.data_remote.dataSource

import com.gma.data_remote.models.ApiDataModel

interface ValidateConnectionDataSource {
    suspend fun validate(params: Params): Boolean

    data class Params(
        val apiData: ApiDataModel,
        val pageNo: Int,
        val pageSize: Int
    )
}