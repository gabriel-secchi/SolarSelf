package com.gma.infrastructure.useCase

import com.gma.infrastructure.model.ApiDataAccessModel

interface ApiDataAccessUseCase {

    suspend fun get(): ApiDataAccessModel?
    suspend fun save(apiDataAccess: ApiDataAccessModel)
}