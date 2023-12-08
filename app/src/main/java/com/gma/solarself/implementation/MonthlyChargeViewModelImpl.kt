package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.useCase.StationMonthUseCase
import com.gma.solarself.model.MonthlyChargeModel
import com.gma.solarself.utils.currentMonth
import com.gma.solarself.utils.currentYear
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class MonthlyChargeViewModelImpl(
    private val stationMonthUseCase: StationMonthUseCase
) : MonthlyChargeViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val referenceDate = MutableLiveData<Date>()
    override val monthlySummary = MutableLiveData<MonthlyChargeModel?>()

    private lateinit var _lastStationId: String

    override fun updateReferenceDate(month: Int, year: Int) {
        val newReferenceDate = Calendar.getInstance().apply {
            set(year, month, 1)
        }
        fetchMonthlySummary(_lastStationId, newReferenceDate.time)
    }

    override fun fetchMonthlySummary(stationId: String, date: Date?) {
        viewModelScope.launch {
            try {
                loading.postValue(true)
                _lastStationId = stationId
                val curDate = date ?: Date()
                val month = curDate.currentMonth()
                val year = curDate.currentYear()

                referenceDate.postValue(curDate)

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
            finally {
                loading.postValue(false)
            }
        }
    }
}