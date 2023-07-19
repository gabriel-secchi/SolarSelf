package com.gma.data_remote.network

import com.gma.data_remote.models.ApiDataModel

interface ApiDataEngine {
    fun saveApiData(apiData: ApiDataModel)
    fun getApiData(): ApiDataModel
}