package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.useCase.ConfigStationUseCase
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.useCase.UpdateDataMonitoringUseCase
import com.gma.solarself.viewModel.SolarDataViewModel
import kotlinx.coroutines.launch

class SolarDataViewModelImpl(
    private val configStationUseCase: ConfigStationUseCase,
    private val configToolbarUseCase: ConfigToolbarUseCase,
    private val updateDataMonitoring: UpdateDataMonitoringUseCase
) : SolarDataViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val monitoredStationId = MutableLiveData<String?>()

    override fun setupMonitoredStation() {
        runSafeWithStationId { stationId ->
            stationId?.let {
                monitoredStationId.postValue(stationId)
            }
        }
    }

    override fun showToolbarConfigButton() {
        configToolbarUseCase.displayConfigButton(isVisible = true)
    }

    override fun updateScreen(stationId: String?) {
        runSafeWithStationId(stationId) { id ->
            id?.let {
                updateDataMonitoring.run(it)
            }
        }
    }

    private fun runSafeWithStationId(stationId: String? = null, callback: suspend (String?) -> Unit) {
        viewModelScope.launch {
            try {
                loading.postValue(true)
                if(stationId.isNullOrBlank()) {
                    configStationUseCase.getConfig().let { id ->
                        callback(id)
                    }
                }
                else {
                    callback(stationId)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                monitoredStationId.postValue(null)
                //TODO: post value error
            } finally {
                loading.postValue(false)
            }
        }
    }
}