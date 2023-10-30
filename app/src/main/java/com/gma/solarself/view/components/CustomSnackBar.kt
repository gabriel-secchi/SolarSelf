package com.gma.solarself.view.components

import android.content.Context
import android.view.View
import com.gma.solarself.R
import com.google.android.material.snackbar.Snackbar

object CustomSnackBar {
    private var snackbar: Snackbar? = null

    fun make(view: View?, message: Any, duration: Int = Snackbar.LENGTH_LONG): CustomSnackBar {
        view?.let {
            snackbar = when(message) {
                is Int -> Snackbar.make(it, message, duration)
                is String -> Snackbar.make(it, message, duration)
                else -> null
            }
            snackbar?.animationMode = Snackbar.ANIMATION_MODE_SLIDE
        }
        return this
    }

    fun setSuccessStyle(context: Context): CustomSnackBar {
        setColor(context, R.color.snackbar_success, R.color.textColorPrimary)
        return this
    }

    fun setErrorStyle(context: Context): CustomSnackBar {
        setColor(context, R.color.snackbar_error, R.color.textColorPrimary)
        return this
    }

    private fun setColor(context: Context, bgColor: Int, textColor: Int) {
        snackbar?.apply {
            setBackgroundTint(context.getColor(bgColor))
            setTextColor(context.getColor(textColor))
        }
    }

    fun show() {
        snackbar?.apply {
            show()
        }
    }
}