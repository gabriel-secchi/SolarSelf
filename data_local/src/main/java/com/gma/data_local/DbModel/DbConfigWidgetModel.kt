package com.gma.data_local.DbModel

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class DbConfigWidgetModel : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var monitored_station_id: String = ""
    var background_color: String = ""
    var text_color: String = ""
}