package com.gma.infrastructure.implementation

import com.gma.data_local.model.PeriodModel
import com.gma.data_local.useCase.ConfigPeriodUseCase
import com.gma.infrastructure.model.ConfigDatePeriodModel
import com.gma.infrastructure.useCase.ConfigDatePeriodUseCase


class ConfigDatePeriodUseCaseImpl(
    private val config: ConfigPeriodUseCase
) : ConfigDatePeriodUseCase {
    override suspend fun getConfig(): ConfigDatePeriodModel? {
        return config.getConfig()?.let {
            ConfigDatePeriodModel(
                it.startDate,
                it.endDate,
                it.autoUpdatePeriod
            )
        }
    }

    override suspend fun saveConfig(periodModel: ConfigDatePeriodModel): Boolean {
        return try {
            config.saveConfig(PeriodModel(
                periodModel.startDate,
                periodModel.endDate,
                periodModel.autoUpdatePeriod,
            ))
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