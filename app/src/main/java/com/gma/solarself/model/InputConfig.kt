package com.gma.solarself.model

import com.gma.solarself.inputValidators.InputValidator

data class InputConfig(
    val validators: List<InputValidator>? = null,
    val required: Boolean = false,
    val validateOnFocusOut: Boolean = false,
    val validateOnTextChang: Boolean = false
)
