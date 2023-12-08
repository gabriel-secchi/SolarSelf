package com.gma.data_local.implementationDao

import com.gma.data_local.DbModel.DbConfigModel
import com.gma.data_local.dao.ConfigWidgetDao
import com.gma.data_local.model.WidgetConfig
import com.gma.data_local.realmDatabase.KEY_CONFIG_WIDGET
import io.realm.kotlin.Realm

class ConfigWidgetDaoImpl(
    private val database: Realm
) : ConfigWidgetDao, CustomDaoImpl<DbConfigModel>(database, DbConfigModel::class) {

    override fun saveConfig(monitoredStationId: String) {

        database.writeBlocking {
            val configSaved = query(DbConfigModel::class).find().firstOrNull()
            configSaved?.apply {
                value = monitoredStationId
            }
                ?: copyToRealm(
                    DbConfigModel().apply {
                        id = KEY_CONFIG_WIDGET
                        value = monitoredStationId
                    }
                )
        }
    }

    override fun getConfig(): WidgetConfig {
        val data = findFirst()
        return data?.let {
            WidgetConfig(
                monitoredStationId = it.value
            )
        } ?: run {
            WidgetConfig(
                monitoredStationId = ""
            )
        }
    }

    override fun deleteConfig(): Boolean {
        try {
            findFirst()?.let {
                delete(it)
                return true
            }
            return false
        } catch (ex: Exception) {
            return false
        }
    }
}