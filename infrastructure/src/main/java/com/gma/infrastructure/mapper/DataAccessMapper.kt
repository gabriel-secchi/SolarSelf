package com.gma.infrastructure.mapper

import com.gma.data_local.model.DataAccess
import com.gma.infrastructure.model.ApiDataAccessModel
import com.gma.data_remote.models.ApiDataModel

fun DataAccess?.toApiDataAccess() =
    this?.let {
        ApiDataAccessModel(
            apiUrl = it.apiUrl,
            keyId = it.keyId,
            keySecret = it.keySecret
        )
    }

fun ApiDataAccessModel.toApiDataModel() = ApiDataModel(
    apiUrl = this.apiUrl,
    keyId = this.keyId,
    keySecret = this.keySecret
)

fun ApiDataAccessModel.toDataAccess() = DataAccess(
    apiUrl = this.apiUrl,
    keyId = this.keyId,
    keySecret = this.keySecret
)

fun DataAccess.toApiDataModel() = ApiDataModel(
    apiUrl = this.apiUrl,
    keyId = this.keyId,
    keySecret = this.keySecret
)