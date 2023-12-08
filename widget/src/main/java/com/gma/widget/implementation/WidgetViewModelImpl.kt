package com.gma.widget.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.model.WidgetConfig
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
                notConfigured = true
                getWidgetConfig()?.let { config ->
                    val stationId = config.monitoredStationId
                    if (appIsConfigured() && stationId.isNotBlank()) {
                        notConfigured = false

                        val data = userStationDataUseCase.getDetail(stationId)
                        userStationData.postValue(data)
                    }
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            } finally {
                loading.postValue(false)
            }
        }
    }

    private suspend fun getWidgetConfig(): WidgetConfig? {
        return widgetConfigUseCase.getConfig()
    }

    private suspend fun appIsConfigured(): Boolean {
        return apiDataAccessUseCase.get() != null
    }
}