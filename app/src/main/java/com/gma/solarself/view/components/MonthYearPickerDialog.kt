package com.gma.solarself.view.components

import android.app.AlertDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gma.solarself.R
import com.gma.solarself.databinding.DialogMonthYearPickerBinding
import java.util.Calendar
import java.util.Date

class MonthYearPickerDialog(val date: Date? = Date()) : DialogFragment() {

    private companion object {
        const val YEARS_BEFORE = 2
    }

    private lateinit var binding: DialogMonthYearPickerBinding
    private var listener: OnDateSetListener? = null

    private val calendarCurrentDate: Calendar by lazy {
        Calendar.getInstance().apply { time = Date() }
    }

    private val minYear = calendarCurrentDate.get(Calendar.YEAR) - YEARS_BEFORE
    private val maxYear = calendarCurrentDate.get(Calendar.YEAR)

    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val receivedDate = date ?: Date()
        binding = DialogMonthYearPickerBinding.inflate(requireActivity().layoutInflater)
        val cal: Calendar = Calendar.getInstance().apply { time = receivedDate }

        binding.pickerMonth.run {
            minValue = 0
            maxValue = 11
            value = cal.get(Calendar.MONTH)
            displayedValues = context.resources.getStringArray(R.array.month_list)
        }

        binding.pickerYear.run {
            val year = cal.get(Calendar.YEAR)
            minValue = minYear
            maxValue = maxYear
            value = year
        }

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_month_year_title)
            .setView(binding.root)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener?.onDateSet(
                    null,
                    binding.pickerYear.value,
                    binding.pickerMonth.value,
                    1
                )
            }
            .setNegativeButton(R.string.cancel) { _, _ -> dialog?.cancel() }
            .create()
    }
}