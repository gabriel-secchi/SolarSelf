package com.gma.data_local.implementationDao

import com.gma.data_local.DbModel.DbConfigApiModel
import com.gma.data_local.dao.ConfigApiDao
import com.gma.data_local.model.DataAccess
import io.realm.kotlin.Realm

class ConfigApiDaoImpl(
    database: Realm
) : ConfigApiDao, CustomDaoImpl<DbConfigApiModel>(database, DbConfigApiModel::class) {

    override fun saveDataAccess(dataAccess: DataAccess) {
        add(
            DbConfigApiModel().apply {
                api_url = dataAccess.apiUrl
                key_id = dataAccess.keyId
                key_secret = dataAccess.keySecret
            }
        )
    }

    override fun getDataAccess(): DataAccess? {
        val data = findFirst()
        return data?.let {
            DataAccess(
                apiUrl = it.api_url,
                keyId = it.key_id,
                keySecret = it.key_secret,
            )
        }
    }
}