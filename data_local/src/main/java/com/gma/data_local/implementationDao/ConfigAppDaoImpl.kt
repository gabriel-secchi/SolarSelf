package com.gma.data_local.implementationDao

import com.gma.data_local.DbModel.DbConfigModel
import com.gma.data_local.dao.ConfigAppDao
import com.gma.data_local.dao.ConfigWidgetDao
import com.gma.data_local.model.WidgetConfig
import com.gma.data_local.realmDatabase.KEY_CONFIG_WIDGET
import io.realm.kotlin.Realm

class ConfigAppDaoImpl(
    private val database: Realm
) : ConfigAppDao {

    override fun getConfig(Key: String): String? {
        val data = database.query(
            DbConfigModel::class,
            "id = '$Key'"
        ).find().firstOrNull()

        return data?.value
    }

    override fun saveConfig(Key: String, value: String) {
        database.writeBlocking {
            val configSaved = query(
                DbConfigModel::class,
                "id = '$Key'"
            ).find().firstOrNull()
            configSaved?.apply {
                this.value = value
            }
                ?: copyToRealm(
                    DbConfigModel().apply {
                        id = Key
                        this.value = value
                    }
                )
        }
    }

    override fun removeConfig(Key: String) {
        database.writeBlocking {
            val configSaved = query(
                DbConfigModel::class,
                "id = '$Key'"
            ).find().firstOrNull()

            configSaved?.also { delete(it) }
        }
    }
}