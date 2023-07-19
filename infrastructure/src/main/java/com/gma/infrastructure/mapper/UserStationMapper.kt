package com.gma.infrastructure.mapper

import com.gma.infrastructure.model.UserStationModel
import com.gma.data_remote.models.StationDetailResultModel

fun StationDetailResultModel.toUserStationModel() =
    this.data?.let {
        UserStationModel(
            id = it.id,
            power = it.power,
            dayEnergy = it.dayEnergy
        )
    }
