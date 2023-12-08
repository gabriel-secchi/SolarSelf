package com.gma.data_local.useCase

interface ConfigMonitoredStationUseCase {
    suspend fun getConfig(): String?
    suspend fun saveConfig(stationId: String)
    suspend fun removeConfig()
}