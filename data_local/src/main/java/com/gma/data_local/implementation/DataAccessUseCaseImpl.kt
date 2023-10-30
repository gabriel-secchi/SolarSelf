package com.gma.data_local.implementation

import com.gma.data_local.dao.ConfigApiDao
import com.gma.data_local.model.DataAccess
import com.gma.data_local.useCase.DataAccessUseCase

class DataAccessUseCaseImpl(
    private val configApiDao: ConfigApiDao
): DataAccessUseCase {
    override suspend fun getDataAccess(): DataAccess? {
        return configApiDao.getDataAccess()
    }

    override suspend fun saveDataAccess(dataAccess: DataAccess) {
        configApiDao.saveDataAccess(dataAccess)
    }

}