package com.gma.solarself.view.config

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import androidx.core.view.isVisible
import com.gma.infrastructure.model.ConfigDatePeriodModel
import com.gma.solarself.databinding.FragmentConfigPeriodCardBinding
import com.gma.solarself.utils.DIFF_DAYS_TO_PERIOD
import com.gma.solarself.utils.buildDateDisplay
import com.gma.solarself.utils.currentDay
import com.gma.solarself.utils.currentYear
import com.gma.solarself.utils.monthOfYear
import com.gma.solarself.utils.subtractDays
import com.gma.solarself.utils.sumDays
import com.gma.solarself.utils.toDate
import com.gma.solarself.utils.toStringFormat
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.view.components.CustomSnackBar
import com.gma.solarself.view.components.CustomTextInputEditText
import com.gma.solarself.viewModel.ConfigPeriodCardViewModel

class ConfigPeriodCardFragment :
    PatternFragment<FragmentConfigPeriodCardBinding, ConfigPeriodCardViewModel>(
        FragmentConfigPeriodCardBinding::inflate,
        ConfigPeriodCardViewModel::class
    ) {
    override fun setupViews() {
        setupEditTextDate(binding.inputPeriodStart, useMaxDate = true)
        setupEditTextDate(binding.inputPeriodEnd, useMinDate = true)
        setupAutoUpdateDate()
        setupClearButton()
    }

    override fun setupObservers() {
        viewModel.periodData.observe(requireActivity(), ::setupPeriodData)
        viewModel.loading.observe(requireActivity(), ::setupLoadingShimmer)
        viewModel.success.observe(requireActivity()) { successMessage ->
            CustomSnackBar
                .make(view, successMessage)
                .setSuccessStyle()
                .show()
        }
        viewModel.error.observe(requireActivity()) { errorMessage ->
            CustomSnackBar
                .make(view, errorMessage)
                .setErrorStyle()
                .show()
        }
    }

    private fun setupPeriodData(periodData: ConfigDatePeriodModel?) {
        periodData?.let { periodDataModel ->
            binding.apply {
                inputPeriodStart.text = periodDataModel.startDate.toStringFormat()
                inputPeriodEnd.text = periodDataModel.endDate.toStringFormat()
                checkAutoUpdate.isChecked = periodDataModel.autoUpdatePeriod
                btnClrInitialDate.isVisible = true
            }
        }
    }

    private fun setupLoadingShimmer(isVisible: Boolean) {
        if (isVisible)
            binding.root.showShimmer(true)
        else {
            binding.root.stopShimmer()
            binding.root.hideShimmer()
        }
    }

    private fun setupAutoUpdateDate() {
        binding.checkAutoUpdate.setOnClickListener {
            saveDatePeriod()
        }
    }

    private fun setupClearButton() {
        binding.btnClrInitialDate.setOnClickListener {
            binding.inputPeriodStart.text = ""
            binding.inputPeriodEnd.text = ""
            binding.checkAutoUpdate.isChecked = false
            setDisplayButtonClear()
            viewModel.clearPeriod()
        }
    }

    private fun setupEditTextDate(
        editText: CustomTextInputEditText,
        useMinDate: Boolean = false,
        useMaxDate: Boolean = false,
    ) {
        editText.OnClickListener(false) {
            openDatePicker(
                editText,
                useMinDate = useMinDate,
                useMaxDate = useMaxDate
            )

        }
    }

    private fun openDatePicker(
        textView: CustomTextInputEditText,
        useMinDate: Boolean = false,
        useMaxDate: Boolean = false
    ) {
        textView.text.let { dateValue ->
            DatePickerDialog(requireContext()).let { datePickerDialog ->
                if (dateValue.isNotBlank()) {
                    dateValue.toDate()?.let {
                        datePickerDialog.updateDate(
                            it.currentYear(),
                            it.monthOfYear(),
                            it.currentDay()
                        )
                    }
                }

                binding.apply {
                    if (useMinDate && inputPeriodStart.text.isNotBlank()) {
                        inputPeriodStart.text.toDate()?.time?.let {
                            datePickerDialog.datePicker.minDate = it
                        }
                    }
                    if (useMaxDate && inputPeriodEnd.text.isNotBlank()) {
                        inputPeriodEnd.text.toDate()?.time?.let {
                            datePickerDialog.datePicker.maxDate = it
                        }
                    }
                }

                datePickerDialog.setOnDateSetListener(
                    applyValidateAndSaveDate(textView)
                )
                datePickerDialog.show()
            }
        }
    }

    private fun applyValidateAndSaveDate(
        textView: CustomTextInputEditText
    ): OnDateSetListener {
        return OnDateSetListener { _, year, month, day ->
            textView.text = buildDateDisplay(year, month, day)
            val newDate = textView.text.toDate()

            binding.apply {
                if (inputPeriodStart.text.isBlank()) {
                    inputPeriodStart.text =
                        newDate?.subtractDays(DIFF_DAYS_TO_PERIOD).toStringFormat()
                }
                if (inputPeriodEnd.text.isBlank()) {
                    inputPeriodEnd.text = newDate?.sumDays(DIFF_DAYS_TO_PERIOD).toStringFormat()
                }
            }

            setDisplayButtonClear()
            saveDatePeriod()
        }
    }

    private fun setDisplayButtonClear() {
        binding.btnClrInitialDate.isVisible = binding.inputPeriodStart.text.isNotBlank()
    }

    private fun saveDatePeriod() {
        viewModel.savePeriod(
            binding.inputPeriodStart.text.toDate(),
            binding.inputPeriodEnd.text.toDate(),
            binding.checkAutoUpdate.isChecked
        )
    }
}