package com.gma.infrastructure.useCase

import com.gma.infrastructure.model.WidgetConfig

interface WidgetConfigUseCase {
    suspend fun getConfig(): WidgetConfig?
    suspend fun saveConfig(monitoredStationId: String): Boolean
    suspend fun deleteConfig(): Boolean
}