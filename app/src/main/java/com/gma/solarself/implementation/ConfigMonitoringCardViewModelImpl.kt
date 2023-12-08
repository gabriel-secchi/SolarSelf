package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.useCase.ConfigStationUseCase
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.solarself.R
import com.gma.solarself.viewModel.ConfigMonitoringCardViewModel
import kotlinx.coroutines.launch

class ConfigMonitoringCardViewModelImpl(
    private val userStationDataUseCase: UserStationDataUseCase,
    private val configStationUseCase: ConfigStationUseCase
) : ConfigMonitoringCardViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val stationList = MutableLiveData<List<StationDataPage>>()
    override val stationIdConfigured = MutableLiveData<String?>()
    override val configUpdated = MutableLiveData<Unit>()
    override val error = MutableLiveData<Int>()

    init {
        loadStationData()
    }

    override fun loadStationData() {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val list = userStationDataUseCase.getList()
                stationList.postValue(list)
                setSelectedStation()
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                loading.postValue(false)
            }

        }
    }

    private suspend fun setSelectedStation() {
        val stationId = configStationUseCase.getConfig()
        stationIdConfigured.postValue(stationId)
    }

    override fun saveWidgetConfig(stationId: String?) {
        updateWidgetConfig {
            if(stationId.isNullOrBlank())
                configStationUseCase.removeConfig()
            else
                configStationUseCase.saveConfig(stationId)
        }
    }

    private fun updateWidgetConfig(callback: suspend () -> Boolean) {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                if (callback())
                    configUpdated.postValue(Unit)
                else
                    throw Exception()
            } catch (ex: Exception) {
                ex.printStackTrace()
                error.postValue(R.string.config_screen_widget_error)
            } finally {
                loading.postValue(false)
            }
        }
    }
}