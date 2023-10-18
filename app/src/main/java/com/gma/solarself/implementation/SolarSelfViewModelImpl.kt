package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.useCase.ApiDataAccessUseCase
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.viewModel.SolarSelfViewModel
import kotlinx.coroutines.launch


class SolarSelfViewModelImpl(
    private val apiDataAccessUseCase: ApiDataAccessUseCase
) : SolarSelfViewModel() {
    override val openData = MutableLiveData<Boolean>()
    override val displayToolbarConfigButton = MutableLiveData<Boolean>()

    init {
        alreadyRegistred()
    }

    private fun alreadyRegistred() {
        viewModelScope.launch {
            val dataAccess = apiDataAccessUseCase.get()
            openData.postValue(dataAccess != null)
        }
    }

    override fun displayConfigButton(isVisible: Boolean) {
        displayToolbarConfigButton.postValue(isVisible)
    }
}