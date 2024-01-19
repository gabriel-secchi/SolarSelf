package com.gma.solarself.implementation

import com.gma.solarself.useCase.UpdateDataMonitoringUseCase
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import com.gma.solarself.viewModel.PeriodChargeViewModel

class UpdateDataMonitoringUseCaseImpl(
    private val realtimeChargeViewModel: PeriodChargeViewModel,
    private val monthlyChargeViewModel: MonthlyChargeViewModel,
    private val periodChargeViewModel: PeriodChargeViewModel
) : UpdateDataMonitoringUseCase {

    override suspend fun run(stationId: String) {
        realtimeChargeViewModel.fetchPeriodSummary(stationId)
        monthlyChargeViewModel.fetchMonthlySummary(stationId)
        periodChargeViewModel.fetchPeriodSummary(stationId)
    }

}