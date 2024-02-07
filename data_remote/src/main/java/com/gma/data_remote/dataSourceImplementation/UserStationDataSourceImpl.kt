package com.gma.data_remote.dataSourceImplementation

import com.gma.data_remote.dataSource.UserStationDataSource
import com.gma.data_remote.models.StationDetailResultModel
import com.gma.data_remote.network.NetworkEngine
import com.gma.data_remote.network.NetworkResult
import com.gma.data_remote.models.StationListResultModel
import com.gma.data_remote.network.STATION_DETAIL
import com.gma.data_remote.network.STATION_LIST
import com.gma.data_remote.requestModel.StationDetailRequest
import com.gma.data_remote.requestModel.testeRequest

class UserStationDataSourceImpl(
    private val networkEngine: NetworkEngine
): UserStationDataSource {

    override suspend fun list(params: UserStationDataSource.ParamsToList): StationListResultModel {
        val response = networkEngine.postRequest(
            path = STATION_LIST,
            body = testeRequest(
                pageNo = params.pageNo,
                pageSize = params.pageSize
            ),
            responseClass = StationListResultModel::class.java
        )

        return when(response) {
            is NetworkResult.Success -> {
                response.data.let {
                    if(it.success == true)
                        return it
                    else
                        throw Exception(it.error ?: it.message ?: "Falha na API stationList")
                }
            }
            is NetworkResult.Error -> throw response.exception
        }
    }

    override suspend fun detail(stationId: String): StationDetailResultModel {
        val response = networkEngine.postRequest(
            path = STATION_DETAIL,
            body = StationDetailRequest(
                id = stationId
            ),
            responseClass = StationDetailResultModel::class.java
        )

        return when(response) {
            is NetworkResult.Success -> response.data
            is NetworkResult.Error -> throw response.exception
        }
    }
}