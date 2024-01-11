package com.gma.data_local.implementation

import com.gma.data_local.dao.ConfigPeriodDao
import com.gma.data_local.model.PeriodModel
import com.gma.data_local.useCase.ConfigPeriodUseCase
import java.util.Date

class ConfigPeriodUseCaseImpl(
    private val configDao: ConfigPeriodDao
) : ConfigPeriodUseCase {
    override suspend fun getConfig(): PeriodModel? {
        val daoModel = configDao.getConfig()
        daoModel?.let {
            return PeriodModel(
                Date(it.startDate),
                Date(it.endDate),
                it.autoUpdate
            )
        }
            ?: return null
    }

    override suspend fun saveConfig(model: PeriodModel) {
        configDao.saveConfig(model)
    }

    override suspend fun removeConfig() {
        configDao.removeConfig()
    }
}