package com.gma.data_local.implementationDao

import com.gma.data_local.DbModel.DbConfigWidgetModel
import com.gma.data_local.dao.ConfigWidgetDao
import com.gma.data_local.model.WidgetConfig
import io.realm.kotlin.Realm

class ConfigWidgetDaoImpl(
    database: Realm
) : ConfigWidgetDao, CustomDaoImpl<DbConfigWidgetModel>(database, DbConfigWidgetModel::class) {

    override fun saveConfig(widgetConfig: WidgetConfig) {
        val config = DbConfigWidgetModel().apply {
            monitored_station_id = widgetConfig.monitoredStationId
            background_color = widgetConfig.backgroundColor
            text_color = widgetConfig.textColor
        }
        val configSaved = findFirst()

        if(configSaved == null) {
            add(config)
        }
        else {
            update(config) { configUpdated ->
                configUpdated.monitored_station_id = widgetConfig.monitoredStationId
            }
        }
    }

    override fun getConfig(): WidgetConfig? {
        val data = findFirst()
        return data?.let {
            WidgetConfig(
                monitoredStationId = it.monitored_station_id,
                backgroundColor = it.background_color,
                textColor = it.text_color
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
        }
        catch (ex: Exception) {
            return false
        }
    }
}