package com.gma.data_local.DbModel

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class DbConfigModel : RealmObject {
    @PrimaryKey
    var id: String = ""
    var value: String = ""
}