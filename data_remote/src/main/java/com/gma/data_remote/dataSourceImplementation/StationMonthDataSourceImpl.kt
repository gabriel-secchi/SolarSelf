package com.gma.data_remote.dataSourceImplementation

import com.gma.data_remote.dataSource.StationMonthDataSource
import com.gma.data_remote.dataSource.UserStationDataSource
import com.gma.data_remote.models.StationDetailResultModel
import com.gma.data_remote.models.StationListResultModel
import com.gma.data_remote.models.StationMonthResultModel
import com.gma.data_remote.network.NetworkEngine
import com.gma.data_remote.network.NetworkResult
import com.gma.data_remote.network.STATION_DETAIL
import com.gma.data_remote.network.STATION_LIST
import com.gma.data_remote.network.STATION_MONTH
import com.gma.data_remote.requestModel.StationDetailRequest
import com.gma.data_remote.requestModel.testeRequest

class StationMonthDataSourceImpl(
    private val networkEngine: NetworkEngine
): StationMonthDataSource {

    override suspend fun list(params: StationMonthDataSource.ParamsToList): StationMonthResultModel {
        val response = networkEngine.postRequest(
            path = STATION_MONTH,
            body = params,
            responseClass = StationMonthResultModel::class.java
        )

        return when(response) {
            is NetworkResult.Success -> response.data
            is NetworkResult.Error -> throw response.exception
        }
    }
}