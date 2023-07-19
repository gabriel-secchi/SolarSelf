package com.gma.widget.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.ApiDataAccessUseCase
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.widget.viewModel.WidgetViewModel
import kotlinx.coroutines.launch

class WidgetViewModelImpl(
    private val apiDataAccessUseCase: ApiDataAccessUseCase,
    private val userStationDataUseCase: UserStationDataUseCase
) : WidgetViewModel() {
    override val userStationData = MutableLiveData<UserStationModel?>()
    override val loading = MutableLiveData<Boolean>()

    override fun fetchUserStationData() {
        viewModelScope.launch {
            try {
                loading.postValue(true)
                if (alreadyConfigured()) {
                    val data = userStationDataUseCase.getDetail("1298491919449016030")
                    userStationData.postValue(data)
                }
            }
            catch (ex: Exception) {
                ex.printStackTrace()
            }
            finally {
                loading.postValue(false)
            }
        }
    }

    private suspend fun alreadyConfigured(): Boolean {
        val dataAccess = apiDataAccessUseCase.get()
        val configured = dataAccess != null
        notConfigured = !configured
        return configured
    }
}