package com.gma.data_local.implementation

import com.gma.data_local.dao.ConfigApiDao
import com.gma.data_local.dao.ConfigWidgetDao
import com.gma.data_local.model.DataAccess
import com.gma.data_local.model.WidgetConfig
import com.gma.data_local.useCase.ConfigWidgetUseCase
import com.gma.data_local.useCase.DataAccessUseCase

class ConfigWidgetUseCaseImpl(
    private val configWidgetDao: ConfigWidgetDao
): ConfigWidgetUseCase {
    override suspend fun getConfig(): WidgetConfig? {
        return configWidgetDao.getConfig()
    }

    override suspend fun saveConfig(widgetConfig: WidgetConfig) {
        configWidgetDao.saveConfig(widgetConfig)
    }

}