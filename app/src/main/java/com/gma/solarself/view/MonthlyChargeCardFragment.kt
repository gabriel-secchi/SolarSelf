package com.gma.solarself.view

import com.gma.solarself.databinding.FragmentDataSummaryBinding
import com.gma.solarself.databinding.FragmentMonthlyChargeCardBinding
import com.gma.solarself.model.MonthlyChargeModel
import com.gma.solarself.utils.twoDecimalPlaces
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import com.gma.solarself.viewModel.SummaryDataViewModel

class MonthlyChargeCardFragment(
    val stationId: String
) : PatternFragment<FragmentMonthlyChargeCardBinding, MonthlyChargeViewModel>(
    FragmentMonthlyChargeCardBinding::inflate,
    MonthlyChargeViewModel::class
) {
    override fun setupViews() {
        viewModel.fetchMonthlySummary(stationId)
    }

    override fun setupObservers() {
        viewModel.referenceMonth.observe(requireActivity()) {
            binding.tvReference.text  = "$it"
        }
        viewModel.monthlySummary.observe(requireActivity(), ::setupMonthlySummary)
    }

    private fun setupMonthlySummary(monthlySummary: MonthlyChargeModel?) {
        monthlySummary?.let {
            binding.tvTotal.text = it.total.toString().plus(" ${it.measureType}")
            binding.tvAverage.text = it.average.twoDecimalPlaces().plus(" ${it.measureType}")
        }
        binding.root.stopShimmer()
        binding.root.hideShimmer()
    }
}