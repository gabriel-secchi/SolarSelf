package com.gma.data_local.DbModel

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.Date

class DbConfigPeriodModel : RealmObject {
    @PrimaryKey
    var id: String = ""
    var startDate: Long = 0
    var endDate: Long = 0
    var autoUpdate: Boolean = false
}