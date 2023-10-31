package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.solarself.viewModel.RealTimeChargeViewModel
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class RealTimeChargeViewModelImpl(
    private val userStationDataUseCase: UserStationDataUseCase
) : RealTimeChargeViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val stationData = MutableLiveData<UserStationModel?>()
    private var realtimeIsActive = true
    private var lastStationIdUsed = ""

    override fun setRealTimeFetching(isActive: Boolean) {
        var restarFetching = false
        if(!realtimeIsActive && isActive)
            restarFetching = true

        realtimeIsActive = isActive
        if(restarFetching)
            runAgain(lastStationIdUsed)
    }

    override fun fetchStationData(stationId: String) {
        viewModelScope.launch {
            lastStationIdUsed = stationId
            loading.postValue(true)
            try {
                val data = userStationDataUseCase.getDetail(stationId)
                stationData.postValue(data)
            }
            catch (ex: Exception) {
                stationData.postValue(null)
            }
            finally {
                loading.postValue(false)
                runAgain(stationId)
            }
        }
    }

    private fun runAgain(stationId: String) {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                if(!realtimeIsActive)
                    return
                fetchStationData(stationId)
            }

        }, RELOAD_TIME)
    }

    private companion object {
        const val RELOAD_TIME = 120000L // == 2 minutes
    }
}