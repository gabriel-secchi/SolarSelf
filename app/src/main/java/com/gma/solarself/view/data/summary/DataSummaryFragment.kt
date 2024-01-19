package com.gma.solarself.view.data.summary

import com.gma.solarself.databinding.FragmentDataSummaryBinding
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.viewModel.PatternViewModel

class DataSummaryFragment(
    private val stationId: String
) : PatternFragment<FragmentDataSummaryBinding, PatternViewModel>(
    FragmentDataSummaryBinding::inflate,
    PatternViewModel::class
) {
    override fun setupViews() {
        setupSummaryComponents()
    }

    override fun setupObservers() {
        // N/A
    }

    private fun setupSummaryComponents() {
        childFragmentManager.apply {
            if (fragments.isEmpty()) {
                beginTransaction()
                    .add(binding.periodChargeCard.id, PeriodChargeCardFragment(stationId))
                    .add(binding.monthlyChargeCard.id, MonthlyChargeCardFragment(stationId))
                    .add(binding.realTimeCard.id, RealTimeChargeCardFragment(stationId))
                    .commit()
            }
        }
    }
}