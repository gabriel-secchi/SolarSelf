package com.gma.solarself.view.data.summary

import androidx.core.view.isVisible
import com.gma.infrastructure.model.ConfigDatePeriodModel
import com.gma.solarself.databinding.FragmentPeriodChargeCardBinding
import com.gma.solarself.model.PeriodChargeModel
import com.gma.solarself.utils.currentDayAndMonth
import com.gma.solarself.utils.twoDecimalPlaces
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.viewModel.PeriodChargeViewModel

class PeriodChargeCardFragment(
    private val stationId: String
) : PatternFragment<FragmentPeriodChargeCardBinding, PeriodChargeViewModel>(
    FragmentPeriodChargeCardBinding::inflate,
    PeriodChargeViewModel::class
) {
    override fun setupViews() {
        viewModel.fetchPeriodSummary(stationId)
    }

    override fun setupObservers() {
        viewModel.referencePeriod.observe(requireActivity(), ::setupReferencePeriodText)
        viewModel.periodSummary.observe(requireActivity(), ::setupPeriodSummary)
        viewModel.loading.observe(requireActivity(), ::setupLoading)
        viewModel.noPeriodConfigured.observe(requireActivity()) { notConfigured ->
            binding.root.isVisible = !notConfigured
        }
    }

    private fun setupLoading(isVisible: Boolean) {
        if (isVisible) {
            binding.root.showShimmer(true)
        } else {
            binding.root.stopShimmer()
            binding.root.hideShimmer()
        }
    }

    private fun setupReferencePeriodText(period: ConfigDatePeriodModel) {
        val initialDate = period.startDate.currentDayAndMonth()
        val finalDate = period.endDate.currentDayAndMonth()
        binding.tvReference.text = initialDate.plus(" - $finalDate")
    }

    private fun setupPeriodSummary(periodSummary: PeriodChargeModel?) {
        periodSummary?.let {
            binding.tvTotal.text = it.total.toString().plus(" ${it.measureType}")
            binding.tvAverage.text = it.average.twoDecimalPlaces().plus(" ${it.measureType}")
        }
    }
}