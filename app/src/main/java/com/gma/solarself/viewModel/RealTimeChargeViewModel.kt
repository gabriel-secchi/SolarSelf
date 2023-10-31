package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.UserStationModel
import com.gma.solarself.model.MonthlyChargeModel

abstract class RealTimeChargeViewModel : ExtendViewModel, ViewModel() {
    abstract val stationData: LiveData<UserStationModel?>

    abstract fun fetchStationData(stationId: String)
    abstract fun setRealTimeFetching(isActive: Boolean)
}