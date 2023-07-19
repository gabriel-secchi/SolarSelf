package com.gma.data_local.implementation

import com.gma.data_local.dao.ConfigApiDao
import com.gma.data_local.model.DataAccess
import com.gma.data_local.useCase.DataAccessUseCase

class DataAccessUseCaseImpl(
    private val customPreferences: ConfigApiDao
): DataAccessUseCase {
    override suspend fun getDataAccess(): DataAccess? {
        return customPreferences.getDataAccess()
    }

    override suspend fun saveDataAccess(dataAccess: DataAccess) {
        customPreferences.saveDataAccess(dataAccess)
    }

}