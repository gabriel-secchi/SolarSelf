package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.model.WidgetConfig
import java.util.Date

abstract class ConfigViewModel : ExtendViewModel, ViewModel() {
    abstract val loggedOut: LiveData<Boolean>
    abstract fun hideToolbarConfigButton()
    abstract fun logOff()

}