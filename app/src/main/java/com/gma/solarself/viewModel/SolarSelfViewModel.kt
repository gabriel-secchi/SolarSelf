package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.solarself.useCase.ConfigToolbarUseCase

abstract class SolarSelfViewModel: ViewModel(), ConfigToolbarUseCase {
    abstract val openData: LiveData<Boolean>
    abstract val displayToolbarConfigButton: LiveData<Boolean>

    abstract fun validateRegistration()
}