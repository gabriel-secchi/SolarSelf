package com.gma.data_local.DbModel

import io.realm.kotlin.types.RealmObject

class DbConfigApiModel : RealmObject {
    var key_id: String = ""
    var api_url: String = ""
    var key_secret: String = ""
}
