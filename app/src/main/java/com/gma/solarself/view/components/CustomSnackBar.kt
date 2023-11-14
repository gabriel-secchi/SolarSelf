package com.gma.solarself.view.components
import android.view.View
import android.widget.TextView
import com.gma.solarself.R
import com.gma.solarself.application.SolarSelfApplication
import com.google.android.material.snackbar.Snackbar

object CustomSnackBar {
    private var snackbar: Snackbar? = null

    private fun setupSnackbarStyle() {
        setupAnimation()
        setupTextAlignment()
        setPatternStyle()
    }

    private fun setupAnimation() {
        snackbar?.animationMode = Snackbar.ANIMATION_MODE_SLIDE
    }

    private fun setupTextAlignment() {
        snackbar?.view?.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)?.apply {
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }
    }

    fun make(view: View?, message: Any, duration: Int = Snackbar.LENGTH_LONG): CustomSnackBar {
        view?.let {
            snackbar = when(message) {
                is Int -> Snackbar.make(it, message, duration)
                is String -> Snackbar.make(it, message, duration)
                else -> null
            }
            setupSnackbarStyle()
        }
        return this
    }

    private fun setPatternStyle() {
        setColor(R.color.primarySurface, R.color.textColorPrimary)
    }

    fun setSuccessStyle(): CustomSnackBar {
        setColor(R.color.snackbar_success, R.color.textColorPrimary)
        return this
    }

    /*fun setWarningStyle(): CustomSnackBar {
        setColor(R.color.snackbar_warning, R.color.textColorPrimary)
        return this
    }*/

    fun setErrorStyle(): CustomSnackBar {
        setColor(R.color.snackbar_error, R.color.textColorPrimary)
        return this
    }

    private fun setColor(bgColor: Int, textColor: Int) {
        val context  = SolarSelfApplication.appContext
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