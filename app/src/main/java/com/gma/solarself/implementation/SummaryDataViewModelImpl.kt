package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.useCase.StationMonthUseCase
import com.gma.solarself.model.MonthlyChargeModel
import com.gma.solarself.viewModel.SummaryDataViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class SummaryDataViewModelImpl : SummaryDataViewModel() {
    override val loading = MutableLiveData(false)
    /*override val referenceMonth = MutableLiveData<String>()
    override val monthlySummary = MutableLiveData<MonthlyChargeModel?>()*/
}