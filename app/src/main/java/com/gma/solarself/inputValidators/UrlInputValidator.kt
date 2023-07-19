package com.gma.solarself.inputValidators

import android.content.Context
import android.util.Patterns
import com.gma.solarself.R

class UrlInputValidator : InputValidator {
    override fun validate(trimmedInputValue: String) {
        val isInvalid = !Patterns.WEB_URL.matcher(trimmedInputValue).matches()
        if (isInvalid)
            throw object : InvalidInputException() {
                override fun getReason(context: Context): String =
                    context.getString(R.string.value_is_not_url)
            }
    }
}