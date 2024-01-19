package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.ConfigDatePeriodModel
import com.gma.infrastructure.model.StationMonthDataModel
import com.gma.infrastructure.useCase.ConfigDatePeriodUseCase
import com.gma.infrastructure.useCase.StationMonthUseCase
import com.gma.solarself.customExceptions.NoPeriodConfigured
import com.gma.solarself.model.ChartDataModel
import com.gma.solarself.model.PeriodChargeModel
import com.gma.solarself.utils.DIFF_DAYS_TO_PERIOD
import com.gma.solarself.utils.currentDay
import com.gma.solarself.utils.currentMonth
import com.gma.solarself.utils.currentYear
import com.gma.solarself.utils.sumDays
import com.gma.solarself.viewModel.PeriodChargeViewModel
import kotlinx.coroutines.launch
import java.util.Date

class PeriodChargeViewModelImpl(
    private val stationMonthUseCase: StationMonthUseCase,
    private val configDatePeriodUseCase: ConfigDatePeriodUseCase
) : PeriodChargeViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val noPeriodConfigured = MutableLiveData<Boolean>()
    override val referencePeriod = MutableLiveData<ConfigDatePeriodModel>()
    override val periodCharge = MutableLiveData<PeriodChargeModel?>()

    override fun fetchPeriodSummary(stationId: String) {
        if (loading.value == true)
            return

        loading.postValue(true)
        viewModelScope.launch {
            try {
                val periodConfig = validateAndGetDatePeriod()
                referencePeriod.postValue(periodConfig)

                val initialMonthData = getDataStationMonth(
                    stationId,
                    periodConfig.startDate
                )

                val finalData = if (!sameMonthAndYear(periodConfig)) {
                    getDataStationMonth(
                        stationId,
                        periodConfig.endDate
                    )
                } else {
                    listOf()
                }

                val data =
                    initialMonthData.filter {
                        it.date.currentDay() >= periodConfig.startDate.currentDay()
                    } +
                            finalData.filter {
                                it.date.currentDay() <= periodConfig.endDate.currentDay()
                            }

                if (data.isNotEmpty()) {
                    val total = data.sumOf { it.energy.toDouble() }

                    periodCharge.postValue(
                        PeriodChargeModel(
                            total = total.toInt(),
                            average = total / data.size,
                            measureType = data.first().energyStr,
                            listEnergy = data.map {
                                ChartDataModel(
                                    money = it.money,
                                    energy = it.energy,
                                    date = it.date
                                )
                            }
                        )
                    )
                } else {
                    periodCharge.postValue(null)
                }
            } catch (ex: NoPeriodConfigured) {
                noPeriodConfigured.postValue(true)
            } catch (ex: Exception) {
                periodCharge.postValue(null)
            } finally {
                loading.postValue(false)
            }
        }
    }

    private suspend fun validateAndGetDatePeriod(): ConfigDatePeriodModel {
        val periodConfig = configDatePeriodUseCase.getConfig() ?: throw NoPeriodConfigured()

        if (periodConfig.autoUpdatePeriod) {
            if (periodConfig.endDate.time < Date().time) {
                //save new period
                configDatePeriodUseCase.saveConfig(
                    ConfigDatePeriodModel(
                        startDate = Date(),
                        endDate = Date().sumDays(DIFF_DAYS_TO_PERIOD),
                        autoUpdatePeriod = true
                    )
                )
                return configDatePeriodUseCase.getConfig() ?: throw NoPeriodConfigured()
            }
        }

        return periodConfig
    }

    private fun sameMonthAndYear(period: ConfigDatePeriodModel): Boolean {
        return period.startDate.currentMonth() == period.endDate.currentMonth()
                && period.startDate.currentYear() == period.startDate.currentYear()
    }

    private suspend fun getDataStationMonth(
        stationId: String,
        referenceDate: Date
    ): List<StationMonthDataModel> {
        return stationMonthUseCase.getData(
            stationId = stationId,
            yearAndMonth = "${referenceDate.currentYear()}-${referenceDate.currentMonth()}"
        )
    }
}