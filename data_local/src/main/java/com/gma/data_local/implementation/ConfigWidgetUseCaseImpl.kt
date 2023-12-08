package com.gma.data_local.implementation

import com.gma.data_local.dao.ConfigApiDao
import com.gma.data_local.dao.ConfigAppDao
import com.gma.data_local.dao.ConfigWidgetDao
import com.gma.data_local.model.DataAccess
import com.gma.data_local.model.WidgetConfig
import com.gma.data_local.realmDatabase.KEY_CONFIG_WIDGET
import com.gma.data_local.useCase.ConfigWidgetUseCase
import com.gma.data_local.useCase.DataAccessUseCase

class ConfigWidgetUseCaseImpl(
    //private val configWidgetDao: ConfigWidgetDao
    private val configAppDao: ConfigAppDao
): ConfigWidgetUseCase {
    override suspend fun getConfig(): WidgetConfig? {
        //return configWidgetDao.getConfig()
        val configValue = configAppDao.getConfig(KEY_CONFIG_WIDGET)
        return configValue?.let {
            WidgetConfig(
                monitoredStationId = configValue
            )
        }
    }

    override suspend fun saveConfig(monitoredStationId: String) {
        //configWidgetDao.saveConfig(monitoredStationId)
        configAppDao.saveConfig(
            KEY_CONFIG_WIDGET,
            monitoredStationId
        )
    }

}