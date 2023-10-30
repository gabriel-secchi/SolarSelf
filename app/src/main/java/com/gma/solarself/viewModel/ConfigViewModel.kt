package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.model.WidgetConfig

abstract class ConfigViewModel : ExtendViewModel, ViewModel() {
    abstract val widgetConfig: LiveData<WidgetConfig?>
    abstract val stationList: LiveData<List<StationDataPage>>
    abstract val widgedConfigUpdated: LiveData<Unit>
    abstract val error: LiveData<Int>

    abstract fun loadStationData()
    abstract fun hideToolbarConfigButton()
    abstract fun saveWidgetConfig(stationId: String?)
    abstract fun deleteWidgetConfig()
}