package com.gma.infrastructure.mapper

import com.gma.data_remote.models.StationMonthResultModel
import com.gma.infrastructure.model.StationMonthDataModel
import java.sql.Timestamp
import java.time.Instant
import java.util.Date

fun StationMonthResultModel.toStationMonthDataModel(): List<StationMonthDataModel> {
    return this.data.map {
        StationMonthDataModel(
            id = it.id,
            money = it.money,
            energy = it.energy,
            energyStr = it.energyStr,
            date = Date(it.date)
        )
    }
}