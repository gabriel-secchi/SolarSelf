package com.gma.data_local.implementationDao

import com.gma.data_local.DbModel.DbConfigPeriodModel
import com.gma.data_local.dao.ConfigPeriodDao
import com.gma.data_local.model.PeriodModel
import com.gma.data_local.realmDatabase.KEY_CONFIG_PERIOD
import io.realm.kotlin.Realm

class ConfigPeriodDaoImpl(
    private val database: Realm
) : ConfigPeriodDao {

    override fun getConfig(): DbConfigPeriodModel? {
        return database.query(
            DbConfigPeriodModel::class,
            "id = '$KEY_CONFIG_PERIOD'"
        ).find().firstOrNull()
    }

    override fun saveConfig(model: PeriodModel) {
        database.writeBlocking {
            val configSaved = query(
                DbConfigPeriodModel::class,
                "id = '$KEY_CONFIG_PERIOD'"
            ).find().firstOrNull()
            configSaved?.apply {
                this.startDate = model.startDate.time
                this.endDate = model.endDate.time
                this.autoUpdate = model.autoUpdatePeriod
            }
                ?: copyToRealm(
                    DbConfigPeriodModel().apply {
                        id = KEY_CONFIG_PERIOD
                        this.startDate = model.startDate.time
                        this.endDate = model.endDate.time
                        this.autoUpdate = model.autoUpdatePeriod
                    }
                )
        }
    }

    override fun removeConfig() {
        database.writeBlocking {
            val configSaved = query(
                DbConfigPeriodModel::class,
                "id = '$KEY_CONFIG_PERIOD'"
            ).find().firstOrNull()

            configSaved?.also { delete(it) }
        }
    }
}