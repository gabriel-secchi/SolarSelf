package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.solarself.viewModel.SolarDataViewModel
import kotlinx.coroutines.launch

class SolarDataViewModelImpl(
    private val userStationDataUseCase: UserStationDataUseCase
) : SolarDataViewModel() {
    override val loading = MutableLiveData(false)
    override val stationData = MutableLiveData<UserStationModel?>()

    init {
        fetchDataAccess()
    }

    override fun fetchDataAccess() {
        viewModelScope.launch {
            val data = userStationDataUseCase.getDetail("1298491919449016030")
            stationData.postValue(data)
        }
    }
}