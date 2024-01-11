package com.gma.solarself.view.data.summary

import androidx.fragment.app.FragmentManager
import com.gma.solarself.databinding.FragmentMonthlyChargeCardBinding
import com.gma.solarself.model.MonthlyChargeModel
import com.gma.solarself.utils.currenMonthAndYear
import com.gma.solarself.utils.twoDecimalPlaces
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.view.components.MonthYearPickerDialog
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import java.util.Date

class MonthlyChargeCardFragment(
    private val stationId: String
) : PatternFragment<FragmentMonthlyChargeCardBinding, MonthlyChargeViewModel>(
    FragmentMonthlyChargeCardBinding::inflate,
    MonthlyChargeViewModel::class
) {

    private val supportFragmentManager: FragmentManager by lazy {
        requireActivity().supportFragmentManager
    }

    override fun setupViews() {
        viewModel.fetchMonthlySummary(stationId)
        binding.tvReference.setOnClickListener {
            MonthYearPickerDialog(viewModel.referenceDate.value).apply {
                setListener { _, year, month, _ ->
                    viewModel.updateReferenceDate(month, year)
                }
                show(supportFragmentManager, MONT_YEAR_PICKER_DIALOG_TAG)
            }
        }
    }

    override fun setupObservers() {
        viewModel.referenceDate.observe(requireActivity(), ::setupReferenceDateText)
        viewModel.monthlySummary.observe(requireActivity(), ::setupMonthlySummary)
        viewModel.loading.observe(requireActivity(), ::setupLoading)
    }

    private fun setupLoading(isVisible: Boolean) {
        if (isVisible) {
            binding.root.showShimmer(true)
        } else {
            binding.root.stopShimmer()
            binding.root.hideShimmer()
        }
    }

    private fun setupReferenceDateText(date: Date) {
        binding.tvReference.text = date.currenMonthAndYear()
    }

    private fun setupMonthlySummary(monthlySummary: MonthlyChargeModel?) {
        monthlySummary?.let {
            binding.tvTotal.text = it.total.toString().plus(" ${it.measureType}")
            binding.tvAverage.text = it.average.twoDecimalPlaces().plus(" ${it.measureType}")
        }
    }

    companion object {
        private const val MONT_YEAR_PICKER_DIALOG_TAG = "MonthYearPickerDialog"
    }
}