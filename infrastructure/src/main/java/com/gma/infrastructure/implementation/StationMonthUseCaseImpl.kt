package com.gma.infrastructure.implementation

import com.gma.data_remote.dataSource.StationMonthDataSource
import com.gma.infrastructure.mapper.toStationMonthDataModel
import com.gma.infrastructure.model.StationMonthDataModel
import com.gma.infrastructure.useCase.StationMonthUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class StationMonthUseCaseImpl(
    private val stationMonthDataSource: StationMonthDataSource
): StationMonthUseCase {
    override suspend fun getData(
        stationId: String,
        yearAndMonth: String
    ): List<StationMonthDataModel> {
        try{
            val result = stationMonthDataSource.list(
                StationMonthDataSource.ParamsToList(
                    id = stationId,
                    month = yearAndMonth
                )
            )

            return result.toStationMonthDataModel()
        }
        catch (ex: Exception) {
            if(ex.message?.contains("too many request") == true) {
                return withContext(Dispatchers.Default) {
                    delay(1500)
                    getData(stationId, yearAndMonth)
                }
            }
            else {
                throw ex
            }
        }
    }
}