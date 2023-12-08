package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.StationDataPage

abstract class ConfigMonitoringCardViewModel : ExtendViewModel, ViewModel() {
    abstract val stationIdConfigured: LiveData<String?>
    abstract val stationList: LiveData<List<StationDataPage>>
    abstract val configUpdated: LiveData<Unit>
    abstract val error: LiveData<Int>

    abstract fun loadStationData()
    abstract fun saveWidgetConfig(stationId: String?)
}