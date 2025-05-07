package com.gma.infrastructure.implementation

import com.gma.infrastructure.mapper.toUserStationModel
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.data_remote.dataSource.UserStationDataSource
import com.gma.infrastructure.mapper.toStationDataPage
import com.gma.infrastructure.model.StationDataPage

class UserStationDataUseCaseImpl(
    private val userStationDataSource: UserStationDataSource
): UserStationDataUseCase {
    override suspend fun getList(): List<StationDataPage> {
        val result = userStationDataSource.list(
            UserStationDataSource.ParamsToList(
                pageNo = 1,
                pageSize = 10
            )
        )

        val records = result.data?.page?.records?.toStationDataPage()
        return records ?: listOf()
    }

    override suspend fun getDetail(userStationId: String): UserStationModel? {
        val result = userStationDataSource.detail(userStationId)
        return result.toUserStationModel()
    }
}