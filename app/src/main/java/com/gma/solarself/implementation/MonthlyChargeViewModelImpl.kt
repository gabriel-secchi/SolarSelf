package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.useCase.StationMonthUseCase
import com.gma.solarself.model.MonthlyChargeModel
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

class MonthlyChargeViewModelImpl(
    private val stationMonthUseCase: StationMonthUseCase
) : MonthlyChargeViewModel() {
    override val referenceMonth = MutableLiveData<String>()
    override val monthlySummary = MutableLiveData<MonthlyChargeModel?>()

    /*init {
        fetchMonthlySummary()
    }*/

    override fun fetchMonthlySummary(stationId: String) {
        viewModelScope.launch {
            try {
                val curDate = LocalDate.now()
                val month = curDate.monthValue
                val year = curDate.year

                referenceMonth.postValue("$month/$year")

                val data = stationMonthUseCase.getData(
                    stationId = stationId,
                    yearAndMonth = "$year-$month"
                )
                if (data.isNotEmpty()) {
                    val total = data.sumOf { it.energy.toDouble() }

                    monthlySummary.postValue(
                        MonthlyChargeModel(
                            total = total.toInt(),
                            average = total / data.size,
                            measureType = data.first().energyStr
                        )
                    )
                } else {
                    monthlySummary.postValue(null)
                }
            } catch (ex: Exception) {
                monthlySummary.postValue(null)
            }
        }
    }
}