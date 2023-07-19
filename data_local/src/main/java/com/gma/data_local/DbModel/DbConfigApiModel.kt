package com.gma.data_local.DbModel

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class DbConfigApiModel : RealmObject {
    @PrimaryKey
    var key_id: String = ""
    var api_url: String = ""
    var key_secret: String = ""

    companion object {
        const val PRIMARY_KEY = "key_id"
    }
}
