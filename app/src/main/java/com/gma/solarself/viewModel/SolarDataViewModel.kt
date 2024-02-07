package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.UserStationModel

abstract class SolarDataViewModel : ExtendViewModel, ViewModel() {
    abstract val monitoredStationId: LiveData<String?>
    abstract val error: LiveData<String>

    abstract fun setupMonitoredStation()
    abstract fun showToolbarConfigButton()
    abstract fun updateScreen(stationId: String? = null)
}