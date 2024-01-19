package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.ConfigDatePeriodModel
import com.gma.solarself.model.PeriodChargeModel

abstract class PeriodChargeViewModel : ExtendViewModel, ViewModel() {
    abstract val noPeriodConfigured: LiveData<Boolean>
    abstract val referencePeriod: LiveData<ConfigDatePeriodModel>
    abstract val periodCharge: LiveData<PeriodChargeModel?>

    abstract fun fetchPeriodSummary(stationId: String)
}