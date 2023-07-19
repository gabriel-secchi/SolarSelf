package com.gma.infrastructure.di

import com.gma.data_local.model.DataAccess
import com.gma.data_remote.network.ApiDataEngine
import com.gma.infrastructure.mapper.toApiDataModel

class SaveRemoteApiDataAccess(
    private val apiDataEngine: ApiDataEngine,
) {

    fun execute(apiDataAccess: DataAccess?) {
        apiDataAccess?.let {
            apiDataEngine.saveApiData(it.toApiDataModel())
        }
    }
}
