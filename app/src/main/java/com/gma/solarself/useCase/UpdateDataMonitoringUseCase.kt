package com.gma.solarself.useCase

interface UpdateDataMonitoringUseCase {
    suspend fun run(stationId: String)
}