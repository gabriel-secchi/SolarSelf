package com.gma.infrastructure.useCase

import com.gma.infrastructure.model.UserStationModel

interface UserStationDataUseCase {
    suspend fun getDetail(userStationId: String): UserStationModel?
}