package com.gma.widget.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.ApiDataAccessUseCase
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.infrastructure.useCase.WidgetConfigUseCase
import com.gma.widget.viewModel.WidgetViewModel
import kotlinx.coroutines.launch

class WidgetViewModelImpl(
    private val apiDataAccessUseCase: ApiDataAccessUseCase,
    private val userStationDataUseCase: UserStationDataUseCase,
    private val widgetConfigUseCase: WidgetConfigUseCase
) : WidgetViewModel() {
    override val userStationData = MutableLiveData<UserStationModel?>()
    override val loading = MutableLiveData<Boolean>()

    override fun fetchUserStationData() {
        viewModelScope.launch {
            try {
                loading.postValue(true)
                if (alreadyConfigured()) {
                    monitoredStationId?.let { stationId ->
                        val data = userStationDataUseCase.getDetail(stationId)
                        userStationData.postValue(data)
                    }
                }
            }
            catch (ex: Exception) {
                ex.printStackTrace()
            }
            finally {
                loading.postValue(false)
            }
        }
    }

    private suspend fun fillMonitoredStation() {
        monitoredStationId = widgetConfigUseCase.getConfig()?.monitoredStationId
    }

    private suspend fun alreadyConfigured(): Boolean {
        val dataAccess = apiDataAccessUseCase.get()
        fillMonitoredStation()

        val configured = apiDataAccessUseCase.get()?.let {
            monitoredStationId?.let {
                true
            }
        } == true

        notConfigured = !configured
        return configured
    }
}