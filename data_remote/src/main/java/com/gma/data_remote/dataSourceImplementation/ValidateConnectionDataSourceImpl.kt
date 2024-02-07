package com.gma.data_remote.dataSourceImplementation

import com.gma.data_remote.dataSource.ValidateConnectionDataSource
import com.gma.data_remote.models.StationListResultModel
import com.gma.data_remote.network.ApiDataEngine
import com.gma.data_remote.network.NetworkEngine
import com.gma.data_remote.network.NetworkResult
import com.gma.data_remote.network.STATION_LIST
import com.gma.data_remote.requestModel.testeRequest

class ValidateConnectionDataSourceImpl(
    private val apiDataAccess: ApiDataEngine,
    private val networkEngine: NetworkEngine
) : ValidateConnectionDataSource {

    override suspend fun validate(params: ValidateConnectionDataSource.Params): Boolean {
        apiDataAccess.saveApiData(params.apiData)
        val response = networkEngine.postRequest(
            path = STATION_LIST,
            body = testeRequest(
                pageNo = params.pageNo,
                pageSize = params.pageSize
            ),
            responseClass = StationListResultModel::class.java
        )

        return when (response) {
            is NetworkResult.Success -> {
                if(response.data.success == true && response.data.data != null)
                    return true
                else
                    throw Exception(response.data.error ?: "Falha na validação da API")
            }
            is NetworkResult.Error -> throw response.exception
        }
    }
}