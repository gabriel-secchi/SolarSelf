package com.gma.infrastructure.implementation

import com.gma.data_local.useCase.ConfigMonitoredStationUseCase
import com.gma.infrastructure.useCase.ConfigStationUseCase


class ConfigStationUseCaseImpl(
    private val config: ConfigMonitoredStationUseCase
) : ConfigStationUseCase {
    override suspend fun getConfig(): String? {
        return config.getConfig()
    }

    override suspend fun saveConfig(stationId: String): Boolean {
        return try {
            config.saveConfig(stationId)
            true
        } catch (ex: Exception) {
            false
        }
    }

    override suspend fun removeConfig(): Boolean {
        return try {
            config.removeConfig()
            true
        } catch (ex: Exception) {
            false
        }
    }
}