package com.gma.solarself.useCase

import com.gma.solarself.model.DataAccessInputConfig

interface DataAccessInputConfigUseCase {
    fun getInputConfig(): DataAccessInputConfig
}