package com.gma.solarself.view.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.gma.solarself.R
import com.gma.solarself.databinding.CustomTextInputEditTextBinding
import com.gma.solarself.inputValidators.InvalidInputException
import com.gma.solarself.model.InputConfig

class CustomTextInputEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.textInputStyle
) : ConstraintLayout(context, attributeSet) {
    private var binding: CustomTextInputEditTextBinding

    var config: InputConfig? = null

    var text: String = ""
        get() = binding.input.text.toString().trim()
        set(value) {
            val trimmedValue = value.trim()
            field = trimmedValue
            binding.input.setText(trimmedValue)
        }

    private var helperText: String? = null
        get() = binding.container.helperText.toString()
        set(value) {
            field = value
            binding.container.helperText = value
        }

    val isValid: Boolean
        get() = validate()

    init {
        binding = CustomTextInputEditTextBinding.inflate(LayoutInflater.from(context), this, true)
        val typeArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CustomTextInputEditText,
            defStyleAttr,
            0
        )

        try {
            val resourceIcon =
                typeArray.getResourceId(R.styleable.CustomTextInputEditText_startIconDrawable, 0)
            val hintText = typeArray.getString(R.styleable.CustomTextInputEditText_hint)

            if (resourceIcon != 0)
                binding.container.setStartIconDrawable(resourceIcon)

            binding.container.hint = hintText

            setupFocusOut()
            setupTextChange()
        } finally {
            typeArray.recycle()
        }
    }

    private fun setupFocusOut() {
        binding.input.setOnFocusChangeListener { _, focus ->
            config?.let {
                if (it.validateOnFocusOut && !focus) {
                    validate()
                }
            }
        }
    }

    private fun setupTextChange() {
        binding.input.addTextChangedListener { _ ->
            config?.let {
                if (it.validateOnTextChang) {
                    validate()
                }
            }
        }
    }

    private fun validate(): Boolean {
        config?.let {
            try {
                if (it.required && text.isEmpty()) {
                    helperText = context.getString(R.string.required_field)
                    return false
                }

                it.validators?.forEach { validator ->
                    validator.validate(text)
                }
            } catch (inputEx: InvalidInputException) {
                helperText = inputEx.getReason(context)
                return false
            } catch (ex: Exception) {
                helperText = context.getString(R.string.invalid_field)
                return false
            }
        }

        helperText = null
        return true
    }
}