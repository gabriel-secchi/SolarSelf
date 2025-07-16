package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.ConfigDatePeriodModel
import com.gma.infrastructure.useCase.ConfigDatePeriodUseCase
import com.gma.solarself.R
import com.gma.solarself.viewModel.ConfigPeriodCardViewModel
import kotlinx.coroutines.launch
import java.util.Date

class ConfigPeriodCardViewModelImpl(
    private val configDatePeriodUseCase: ConfigDatePeriodUseCase
) : ConfigPeriodCardViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val error = MutableLiveData<Int>()
    override val success = MutableLiveData<Int>()
    override val periodData = MutableLiveData<ConfigDatePeriodModel?>()

    init {
        loadPerioData()
    }

    fun loadPerioData() {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                periodData.postValue(configDatePeriodUseCase.getConfig())
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                loading.postValue(false)
            }

        }
    }

    override fun savePeriod(startDate: Date?, endDate: Date?, autoUpdatePeriod: Boolean) {
        viewModelScope.launch {
            if (startDate == null) {
                postError(R.string.config_screen_period_start_date_invalid)
                return@launch
            }
            if (endDate == null) {
                postError(R.string.config_screen_period_end_date_invalid)
                return@launch
            }

            val result = configDatePeriodUseCase.saveConfig(
                ConfigDatePeriodModel(
                    startDate, endDate, autoUpdatePeriod
                )
            )

            if (result) {
                success.postValue(R.string.config_screen_period_save_success)
            } else {
                postError(
                    R.string.config_screen_period_save_error,
                    true
                )
            }
        }
    }

    override fun clearPeriod() {
        viewModelScope.launch {
            val result = configDatePeriodUseCase.removeConfig()
            if (result) {
                success.postValue(R.string.config_screen_period_clear_success)
            } else {
                postError(
                    R.string.config_screen_period_clear_error,
                    true
                )
            }
        }
    }

    private fun postError(errorMessage: Int, reloadData: Boolean = false) {
        error.postValue(errorMessage)
        if(reloadData)
            loadPerioData()
    }
}