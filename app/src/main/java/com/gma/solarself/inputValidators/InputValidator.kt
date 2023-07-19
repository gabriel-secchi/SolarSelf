package com.gma.solarself.inputValidators

interface InputValidator {
    @Throws(InvalidInputException::class)
    fun validate(trimmedInputValue: String)
}