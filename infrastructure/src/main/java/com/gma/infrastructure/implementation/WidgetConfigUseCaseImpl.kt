package com.gma.infrastructure.implementation

import com.gma.data_local.dao.ConfigWidgetDao
import com.gma.infrastructure.mapper.toWidgetConfigModel
import com.gma.infrastructure.model.WidgetConfig
import com.gma.infrastructure.useCase.WidgetConfigUseCase

class WidgetConfigUseCaseImpl(
    private val configWidgetDao: ConfigWidgetDao
) : WidgetConfigUseCase {

    override suspend fun getConfig(): WidgetConfig? {
        return configWidgetDao.getConfig()?.toWidgetConfigModel()
    }

    override suspend fun saveConfig(monitoredStationId: String): Boolean {
        return try {
            configWidgetDao.saveConfig( monitoredStationId )
            true
        } catch (ex: Exception) {
            false
        }
    }

    override suspend fun deleteConfig(): Boolean {
        return configWidgetDao.deleteConfig()
    }
}