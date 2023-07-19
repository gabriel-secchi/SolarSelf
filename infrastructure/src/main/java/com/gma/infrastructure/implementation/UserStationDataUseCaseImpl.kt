package com.gma.infrastructure.implementation

import com.gma.infrastructure.mapper.toUserStationModel
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.data_remote.dataSource.UserStationDataSource

class UserStationDataUseCaseImpl(
    private val userStationDataSource: UserStationDataSource
): UserStationDataUseCase {
    override suspend fun getDetail(userStationId: String): UserStationModel? {
        val result = userStationDataSource.detail(userStationId)
        return result.toUserStationModel()
    }
}