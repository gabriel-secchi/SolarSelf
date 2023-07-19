package com.gma.infrastructure.useCase

import com.gma.infrastructure.model.ApiDataAccessModel

interface ApiAccessValidationUseCase {
    suspend fun accessGranted(apiDataAccessModel: ApiDataAccessModel): Boolean
}