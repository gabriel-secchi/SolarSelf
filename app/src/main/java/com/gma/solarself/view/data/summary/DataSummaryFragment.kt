package com.gma.solarself.view.data.summary

import com.gma.solarself.databinding.FragmentDataSummaryBinding
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.viewModel.SummaryDataViewModel

class DataSummaryFragment(
    private val stationId: String
) : PatternFragment<FragmentDataSummaryBinding, SummaryDataViewModel>(
    FragmentDataSummaryBinding::inflate,
    SummaryDataViewModel::class
) {
    override fun setupViews() {
        setupSummaryComponents()
    }

    override fun setupObservers() {
        // N/A
    }

    private fun setupSummaryComponents() {
        view?.post {
            childFragmentManager.beginTransaction()
                .replace(binding.periodChargeCard.id, PeriodChargeCardFragment(stationId))
                .replace(binding.monthlyChargeCard.id, MonthlyChargeCardFragment(stationId))
                .replace(binding.realTimeCard.id, RealTimeChargeCardFragment(stationId))
                .commit()
        }
    }
}