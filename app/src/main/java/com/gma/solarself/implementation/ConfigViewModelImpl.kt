package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.useCase.ApiDataAccessUseCase
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.viewModel.ConfigViewModel
import kotlinx.coroutines.launch

class ConfigViewModelImpl(
    private val configToolbarUseCase: ConfigToolbarUseCase,
    private val apiDataAccessUseCase: ApiDataAccessUseCase
) : ConfigViewModel() {
    override val loggedOut = MutableLiveData<Boolean>()
    override val loading = MutableLiveData<Boolean>()

    override fun hideToolbarConfigButton() {
        configToolbarUseCase.displayConfigButton(isVisible = false)
    }

    override fun logOff() {
        viewModelScope.launch {
            try {
                loading.postValue(true)
                val deleteddata = apiDataAccessUseCase.delete()
                loggedOut.postValue(deleteddata)
            } catch (ex: Exception) {
                ex.printStackTrace()
                loggedOut.postValue(false)
            } finally {
                loading.postValue(true)
            }
        }
    }
}