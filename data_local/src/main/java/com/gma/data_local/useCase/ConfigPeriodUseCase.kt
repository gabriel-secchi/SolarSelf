package com.gma.data_local.useCase

import com.gma.data_local.model.PeriodModel

interface ConfigPeriodUseCase {
    suspend fun getConfig(): PeriodModel?
    suspend fun saveConfig(model: PeriodModel)
    suspend fun removeConfig()
}