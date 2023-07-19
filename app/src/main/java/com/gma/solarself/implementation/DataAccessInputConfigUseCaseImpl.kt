package com.gma.solarself.implementation

import com.gma.solarself.inputValidators.UrlInputValidator
import com.gma.solarself.model.DataAccessInputConfig
import com.gma.solarself.model.InputConfig
import com.gma.solarself.useCase.DataAccessInputConfigUseCase

class DataAccessInputConfigUseCaseImpl : DataAccessInputConfigUseCase {
    override fun getInputConfig(): DataAccessInputConfig {
        return DataAccessInputConfig(
            urlInput = getUrlInputValidators(),
            keyIdInput = InputConfig(required = true, validateOnTextChang = true),
            keySecretInput = InputConfig(required = true, validateOnTextChang = true)
        )
    }

    private fun getUrlInputValidators() = InputConfig(
        validators = listOf(UrlInputValidator()),
        required = true,
        validateOnFocusOut = true
    )
}