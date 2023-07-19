package com.gma.infrastructure.implementation

import com.gma.infrastructure.mapper.toApiDataModel
import com.gma.infrastructure.mapper.toDataAccess
import com.gma.infrastructure.model.ApiDataAccessModel
import com.gma.infrastructure.useCase.ApiAccessValidationUseCase
import com.gma.data_remote.dataSource.ValidateConnectionDataSource
import com.gma.infrastructure.useCase.ApiDataAccessUseCase

class ApiAccessValidationUseCaseimpl(
    private val apiDataAccessUseCase: ApiDataAccessUseCase,
    private val validateConnectionDataSource: ValidateConnectionDataSource
) : ApiAccessValidationUseCase {
    override suspend fun accessGranted(apiDataAccessModel: ApiDataAccessModel): Boolean {
        try {
            val isValid = validateConnectionDataSource.validate(
                ValidateConnectionDataSource.Params(
                    apiData = apiDataAccessModel.toApiDataModel(),
                    pageNo = 1,
                    pageSize = 10
                )
            )
            if (isValid) {
                apiDataAccessUseCase.save( apiDataAccessModel )
            }
            return isValid
        }
        catch (ex: Exception) {
            return false
        }
    }

}