package com.gma.solarself.view.components

data class Validator(
    val isValid: () -> Boolean,
    val errorMessage: String
)
