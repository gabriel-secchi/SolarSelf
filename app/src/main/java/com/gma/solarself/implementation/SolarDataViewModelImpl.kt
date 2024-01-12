package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.ConfigStationUseCase
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.viewModel.SolarDataViewModel
import kotlinx.coroutines.launch

class SolarDataViewModelImpl(
    private val configStationUseCase: ConfigStationUseCase,
    private val configToolbarUseCase: ConfigToolbarUseCase
) : SolarDataViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val monitoredStationId = MutableLiveData<String?>()

    override fun setupMonitoredStation() {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                configStationUseCase.getConfig().let { stationId ->
                    monitoredStationId.postValue(stationId)
                }
            }
            catch (ex: Exception) {
                ex.printStackTrace()
                monitoredStationId.postValue(null)
                //TODO: post value error
            }
            finally {
                loading.postValue(false)
            }
        }
    }

    override fun showToolbarConfigButton() {
        configToolbarUseCase.displayConfigButton(isVisible = true)
    }
}