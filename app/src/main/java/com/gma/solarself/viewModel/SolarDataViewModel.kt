package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.UserStationModel

abstract class SolarDataViewModel : ExtendViewModel, ViewModel() {
    abstract val stationData: LiveData<UserStationModel?>

    abstract fun fetchDataAccess()
}