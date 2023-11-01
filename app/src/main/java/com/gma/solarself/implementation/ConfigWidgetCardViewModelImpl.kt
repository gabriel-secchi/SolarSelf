package com.gma.solarself.implementation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.model.WidgetConfig
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.infrastructure.useCase.WidgetConfigUseCase
import com.gma.solarself.R
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.viewModel.ConfigViewModel
import com.gma.solarself.viewModel.ConfigWidgetCardViewModel
import kotlinx.coroutines.launch

class ConfigWidgetCardViewModelImpl(
    private val userStationDataUseCase: UserStationDataUseCase,
    private val widgetConfigUseCase: WidgetConfigUseCase
) : ConfigWidgetCardViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val stationList = MutableLiveData<List<StationDataPage>>()
    override val widgetConfig = MutableLiveData<WidgetConfig?>()
    override val widgedConfigUpdated = MutableLiveData<Unit>()
    override val error = MutableLiveData<Int>()

    init {
        loadStationData()
    }

    override fun loadStationData() {
        viewModelScope.launch {
            loading.postValue(true)
            try {
                val list = userStationDataUseCase.getList()
                stationList.postValue(list)
                setSelectedStation()
            }
            catch (ex: Exception) {
                ex.printStackTrace()
            }
            finally {
                loading.postValue(false)
            }

        }
    }

    private suspend fun setSelectedStation() {
        val config = widgetConfigUseCase.getConfig()
        widgetConfig.postValue(config)
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