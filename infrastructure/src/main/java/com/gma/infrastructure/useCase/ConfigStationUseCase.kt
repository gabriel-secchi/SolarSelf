package com.gma.infrastructure.useCase

interface ConfigStationUseCase {
    suspend fun getConfig(): String?
    suspend fun saveConfig(stationId: String): Boolean
    suspend fun removeConfig(): Boolean
}