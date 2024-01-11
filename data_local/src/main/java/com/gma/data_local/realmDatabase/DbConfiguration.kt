package com.gma.data_local.realmDatabase

import com.gma.data_local.DbModel.DbConfigApiModel
import com.gma.data_local.DbModel.DbConfigModel
import com.gma.data_local.DbModel.DbConfigPeriodModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

fun openDatabase(): Realm {
    val config = RealmConfiguration.Builder(
        setOf(
            DbConfigApiModel::class,
            DbConfigModel::class,
            DbConfigPeriodModel::class
        ))
        .name(DB_NAME)
        .build()

    return Realm.open(config)
}

private const val DB_NAME = "solar_self_db"