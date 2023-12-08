package com.gma.data_local.implementation

import com.gma.data_local.dao.ConfigAppDao
import com.gma.data_local.realmDatabase.KEY_CONFIG_MONITORED_STATION
import com.gma.data_local.useCase.ConfigMonitoredStationUseCase

class ConfigMonitoredStationUseCaseImpl(
    private val configDao: ConfigAppDao
): ConfigMonitoredStationUseCase {
    override suspend fun getConfig(): String? {
        return configDao.getConfig(KEY_CONFIG_MONITORED_STATION)
    }

    override suspend fun saveConfig(stationId: String) {
        configDao.saveConfig(KEY_CONFIG_MONITORED_STATION, stationId)
    }

    override suspend fun removeConfig() {
        configDao.removeConfig(KEY_CONFIG_MONITORED_STATION)
    }
}