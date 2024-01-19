package com.gma.solarself.view.data.body

import com.gma.solarself.databinding.FragmentDataBodyBinding
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.viewModel.PatternViewModel

class DataBodyFragment(
    private val stationId: String
) : PatternFragment<FragmentDataBodyBinding, PatternViewModel>(
    FragmentDataBodyBinding::inflate,
    PatternViewModel::class
) {
    override fun setupViews() {
        setupBodyComponents()
    }

    override fun setupObservers() {
        // N/A
    }

    private fun setupBodyComponents() {
        childFragmentManager.apply {
            if (fragments.isEmpty())
                beginTransaction()
                    .add(binding.periodChargeChart.id, PeriodChargeChartFragment(stationId))
                    .add(binding.monthlyChargeChart.id, MonthlyChargeChartFragment(stationId))
                    .commit()
        }
    }
}