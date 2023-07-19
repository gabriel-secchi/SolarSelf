package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class SolarSelfViewModel: ViewModel() {
    abstract val openData: LiveData<Boolean>
}