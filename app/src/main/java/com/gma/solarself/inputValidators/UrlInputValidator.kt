package com.gma.solarself.inputValidators

import android.content.Context
import com.gma.solarself.R
import java.net.MalformedURLException
import java.net.URL

class UrlInputValidator : InputValidator {
    override fun validate(trimmedInputValue: String) {
        val isInvalid = isValidUrl(trimmedInputValue).not()
        if (isInvalid)
            throw object : InvalidInputException() {
                override fun getReason(context: Context): String =
                    context.getString(R.string.value_is_not_url)
            }
    }

    private fun isValidUrl(url: String): Boolean {
        return try {
            val parsedUrl = URL(url)
            parsedUrl.protocol == HTTP || parsedUrl.protocol == HTTPS
        } catch (_: MalformedURLException) {
            false
        }
    }

    private companion object {
        private const val HTTP = "http"
        private const val HTTPS = "https"
    }
}