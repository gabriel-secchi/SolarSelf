package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.model.WidgetConfig

abstract class ConfigViewModel : ViewModel() {
    abstract fun hideToolbarConfigButton()
}