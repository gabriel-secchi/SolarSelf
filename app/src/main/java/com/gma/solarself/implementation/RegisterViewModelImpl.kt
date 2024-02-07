package com.gma.solarself.implementation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.model.ApiDataAccessModel
import com.gma.infrastructure.useCase.ApiAccessValidationUseCase
import com.gma.solarself.model.DataAccessInputConfig
import com.gma.solarself.useCase.DataAccessInputConfigUseCase
import com.gma.solarself.viewModel.RegisterViewModel
import kotlinx.coroutines.launch

class RegisterViewModelImpl(
    private val inputConfigUseCase: DataAccessInputConfigUseCase,
    private val validationConnection: ApiAccessValidationUseCase
) : RegisterViewModel() {
    override val loading = MutableLiveData<Boolean>()
    override val inputsConfig = MutableLiveData<DataAccessInputConfig>()
    override val successDataSaved = MutableLiveData<Boolean>()
    override val errorMessage = MutableLiveData<String>()

    init {
        getConfigInputs()
    }

    private fun setLoading(isVisible: Boolean) = loading.postValue(isVisible)

    private fun getConfigInputs() {
        val config = inputConfigUseCase.getInputConfig()
        inputsConfig.postValue(config)
    }

    override fun saveDataAccess(data: SaveApiParams) {
        viewModelScope.launch {
            try {
                setLoading(true)
                val validConnection = validationConnection.accessGranted(
                    ApiDataAccessModel(
                        apiUrl = data.url,
                        keyId = data.keyId,
                        keySecret = data.keySecret
                    )
                )

                successDataSaved.postValue(validConnection)
                if(!validConnection)
                    throw Exception("Não foi possível validar a conexão, tente novamente mais tarde")
            } catch (ex: Exception) {
                errorMessage.postValue(ex.message)
            } finally {
                setLoading(false)
            }

        }
    }

}