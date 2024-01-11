package com.gma.infrastructure.useCase

import com.gma.infrastructure.model.ConfigDatePeriodModel

interface ConfigDatePeriodUseCase {
    suspend fun getConfig(): ConfigDatePeriodModel?
    suspend fun saveConfig(periodModel: ConfigDatePeriodModel): Boolean
    suspend fun removeConfig(): Boolean
}