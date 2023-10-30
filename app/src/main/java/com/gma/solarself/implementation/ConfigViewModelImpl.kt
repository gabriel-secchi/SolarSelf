package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.model.WidgetConfig
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.infrastructure.useCase.WidgetConfigUseCase
import com.gma.solarself.R
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.viewModel.ConfigViewModel
import kotlinx.coroutines.launch

class ConfigViewModelImpl(
    private val userStationDataUseCase: UserStationDataUseCase,
    private val configToolbarUseCase: ConfigToolbarUseCase,
    private val widgetConfigUseCase: WidgetConfigUseCase
) : ConfigViewModel() {
    override val loading = MutableLiveData(false)
    override val stationList = MutableLiveData<List<StationDataPage>>()
    override val widgetConfig = MutableLiveData<WidgetConfig?>()
    override val widgedConfigUpdated = MutableLiveData<Unit>()
    override val error = MutableLiveData<Int>()

    init {
        loadStationData()
    }

    override fun loadStationData() {
        viewModelScope.launch {
            val list = userStationDataUseCase.getList()
            stationList.postValue(list)
            setSelectedStation()
        }
    }

    override fun hideToolbarConfigButton() {
        configToolbarUseCase.displayConfigButton(isVisible = false)
    }

    private fun setSelectedStation() {
        viewModelScope.launch {
            val config = widgetConfigUseCase.getConfig()
            widgetConfig.postValue(config)
        }
    }

    override fun saveWidgetConfig(stationId: String?) {
        updateWidgetConfig {
            widgetConfigUseCase.saveConfig(
                WidgetConfig(
                    monitoredStationId = stationId ?: "",
                    backgroundColor = "",
                    textColor = ""
                )
            )
        }
    }

    override fun deleteWidgetConfig() {
        updateWidgetConfig {
            widgetConfigUseCase.deleteConfig()
        }
    }

    private fun updateWidgetConfig(callback: suspend () -> Boolean) {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                if (callback())
                    widgedConfigUpdated.postValue(Unit)
                else
                    throw Exception()
            }
            catch (ex: Exception) {
                error.postValue(R.string.config_screen_widget_error)
            }
            finally {
                loading.postValue(false)
            }
        }
    }
}