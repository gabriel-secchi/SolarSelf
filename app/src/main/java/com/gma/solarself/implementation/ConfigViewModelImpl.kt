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
    private val configToolbarUseCase: ConfigToolbarUseCase
) : ConfigViewModel() {
    override fun hideToolbarConfigButton() {
        configToolbarUseCase.displayConfigButton(isVisible = false)
    }
}