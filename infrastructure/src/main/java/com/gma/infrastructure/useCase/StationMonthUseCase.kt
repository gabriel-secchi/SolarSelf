package com.gma.infrastructure.useCase

import com.gma.infrastructure.model.StationMonthDataModel

interface StationMonthUseCase {
    suspend fun getData(stationId: String, yearAndMonth: String): List<StationMonthDataModel>
}