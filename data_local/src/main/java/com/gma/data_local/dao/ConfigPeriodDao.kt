package com.gma.data_local.dao

import com.gma.data_local.DbModel.DbConfigPeriodModel
import com.gma.data_local.model.PeriodModel

interface ConfigPeriodDao {
    fun getConfig(): DbConfigPeriodModel?
    fun saveConfig(model: PeriodModel)
    fun removeConfig()
}