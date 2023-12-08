package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.useCase.ConfigStationUseCase
import com.gma.solarself.viewModel.SummaryDataViewModel
import kotlinx.coroutines.launch

class SummaryDataViewModelImpl(
    private val configStationUseCase: ConfigStationUseCase
) : SummaryDataViewModel() {
    override val loading = MutableLiveData(false)
    override val monitoredStationId = MutableLiveData<String>()

    override fun setupMonitoredStation() {
        viewModelScope.launch {
            configStationUseCase.getConfig()?.let { stationId ->
                monitoredStationId.postValue(stationId)
            }
        }
    }
}