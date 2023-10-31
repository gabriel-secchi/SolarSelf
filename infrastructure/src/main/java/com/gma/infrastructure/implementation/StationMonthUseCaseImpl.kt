package com.gma.infrastructure.implementation

import com.gma.data_remote.dataSource.StationMonthDataSource
import com.gma.data_remote.dataSource.UserStationDataSource
import com.gma.infrastructure.mapper.toStationDataPage
import com.gma.infrastructure.mapper.toStationMonthDataModel
import com.gma.infrastructure.mapper.toUserStationModel
import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.model.StationMonthDataModel
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.StationMonthUseCase

class StationMonthUseCaseImpl(
    private val stationMonthDataSource: StationMonthDataSource
): StationMonthUseCase {
    override suspend fun getData(
        stationId: String,
        yearAndMonth: String
    ): List<StationMonthDataModel> {
        val result = stationMonthDataSource.list(
            StationMonthDataSource.ParamsToList(
                id = stationId,
                month = yearAndMonth
            )
        )

        return result.toStationMonthDataModel()
    }
}