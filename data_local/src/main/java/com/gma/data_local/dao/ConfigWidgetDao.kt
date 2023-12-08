package com.gma.data_local.dao

import com.gma.data_local.model.WidgetConfig

interface ConfigWidgetDao {
    fun getConfig(): WidgetConfig?
    fun saveConfig(monitoredStationId: String)
    fun deleteConfig(): Boolean
}