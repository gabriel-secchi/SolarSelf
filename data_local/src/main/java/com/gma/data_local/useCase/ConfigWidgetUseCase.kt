package com.gma.data_local.useCase

import com.gma.data_local.model.WidgetConfig

interface ConfigWidgetUseCase {
    suspend fun getConfig(): WidgetConfig?
    suspend fun saveConfig(monitoredStationId: String)
}