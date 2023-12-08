package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class SummaryDataViewModel : ExtendViewModel, ViewModel() {
    abstract val monitoredStationId: LiveData<String>
    abstract fun setupMonitoredStation()
}