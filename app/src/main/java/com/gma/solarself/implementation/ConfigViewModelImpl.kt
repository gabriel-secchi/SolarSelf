package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.solarself.viewModel.ConfigViewModel

class ConfigViewModelImpl(
    private val userStationDataUseCase: UserStationDataUseCase
) : ConfigViewModel() {
    override val loading = MutableLiveData(false)
    /*override val stationData = MutableLiveData<UserStationModel?>()

    init {
        fetchDataAccess()
    }

    override fun fetchDataAccess() {
        viewModelScope.launch {
            val data = userStationDataUseCase.getDetail("1298491919449016030")
            stationData.postValue(data)
        }
    }*/
}