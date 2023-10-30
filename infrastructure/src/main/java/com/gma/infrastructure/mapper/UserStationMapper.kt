package com.gma.infrastructure.mapper

import com.gma.infrastructure.model.UserStationModel
import com.gma.data_remote.models.StationDetailResultModel
import com.gma.data_remote.models.StationRecord
import com.gma.infrastructure.model.StationDataPage

fun StationDetailResultModel.toUserStationModel() =
    this.data?.let {
        UserStationModel(
            id = it.id,
            power = it.power,
            dayEnergy = it.dayEnergy
        )
    }

fun List<StationRecord>.toStationDataPage() =
    map {
        StationDataPage(
            id = it.id,
            serialNo = it.sno
        )
    }

