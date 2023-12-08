package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.solarself.model.MonthlyChargeModel
import java.util.Date

abstract class MonthlyChargeViewModel : ExtendViewModel, ViewModel() {
    abstract val referenceDate: LiveData<Date>
    abstract val monthlySummary: LiveData<MonthlyChargeModel?>

    abstract fun fetchMonthlySummary(stationId: String, date: Date? = null)
    abstract fun updateReferenceDate(month: Int, year: Int)
}