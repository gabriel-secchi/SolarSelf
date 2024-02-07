package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.solarself.model.DataAccessInputConfig

abstract class RegisterViewModel : ExtendViewModel, ViewModel() {
    abstract val inputsConfig: LiveData<DataAccessInputConfig>
    abstract val successDataSaved: LiveData<Boolean>
    abstract val errorMessage: LiveData<String>

    abstract fun saveDataAccess(data: SaveApiParams)

    data class SaveApiParams(
        val url: String,
        val keyId: String,
        val keySecret: String
    )
}