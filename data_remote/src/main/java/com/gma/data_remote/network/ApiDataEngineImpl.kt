package com.gma.data_remote.network

import com.gma.data_remote.models.ApiDataModel

class ApiDataEngineImpl: ApiDataEngine {
    private var savedApiData: ApiDataModel? = null

    override fun saveApiData(apiData: ApiDataModel) {
        savedApiData = apiData
    }

    override fun getApiData(): ApiDataModel {
        savedApiData?.let {
            return it
        }

        throw Exception("") //TODO: Validate and show error
    }
}