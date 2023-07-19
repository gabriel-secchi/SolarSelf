package com.gma.data_local.useCase

import com.gma.data_local.model.DataAccess

interface DataAccessUseCase {
    suspend fun getDataAccess(): DataAccess?
    suspend fun saveDataAccess(dataAccess: DataAccess)
}