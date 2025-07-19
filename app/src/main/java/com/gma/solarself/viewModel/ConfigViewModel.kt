package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ConfigViewModel : ExtendViewModel, ViewModel() {
    abstract val loggedOut: LiveData<Boolean>
    abstract fun hideToolbarConfigButton()
    abstract fun logOff()
}