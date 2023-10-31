package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.solarself.model.MonthlyChargeModel

abstract class MonthlyChargeViewModel : ViewModel() {
    abstract val referenceMonth: LiveData<String>
    abstract val monthlySummary: LiveData<MonthlyChargeModel?>

    abstract fun fetchMonthlySummary(stationId: String)
}