package com.gma.infrastructure.implementation

import com.gma.data_local.useCase.DataAccessUseCase
import com.gma.infrastructure.di.SaveRemoteApiDataAccess
import com.gma.infrastructure.mapper.toApiDataAccess
import com.gma.infrastructure.mapper.toDataAccess
import com.gma.infrastructure.model.ApiDataAccessModel
import com.gma.infrastructure.useCase.ApiDataAccessUseCase

class ApiDataAccessUseCaseImpl(
    private val dataAccessUseCase: DataAccessUseCase,
    private val saveRemoteApiDataAccess: SaveRemoteApiDataAccess,
): ApiDataAccessUseCase {
    override suspend fun get(): ApiDataAccessModel? {
        val dataAccess = dataAccessUseCase.getDataAccess()
        saveRemoteApiDataAccess.execute(dataAccess)
        return dataAccess.toApiDataAccess()
    }

    /**
     * Não chamar diretamente essa função.
     * Usar sempre o ApiAccessValidationUseCase.accessGranted, que valida e salva a informação de acesso
     */
    override suspend fun save(apiDataAccess: ApiDataAccessModel) {
        val dataAccess = apiDataAccess.toDataAccess()
        dataAccessUseCase.saveDataAccess(dataAccess)
        saveRemoteApiDataAccess.execute(dataAccess)
    }
}