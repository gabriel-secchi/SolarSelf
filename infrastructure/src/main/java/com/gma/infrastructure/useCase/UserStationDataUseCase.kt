package com.gma.infrastructure.useCase

import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.model.UserStationModel

interface UserStationDataUseCase {
    suspend fun getList(): List<StationDataPage>
    suspend fun getDetail(userStationId: String): UserStationModel?
}