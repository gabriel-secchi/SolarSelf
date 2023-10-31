package com.gma.solarself.view

import com.gma.solarself.databinding.FragmentDataSummaryBinding
import com.gma.solarself.viewModel.SummaryDataViewModel

class DataSummaryFragment : PatternFragment<FragmentDataSummaryBinding, SummaryDataViewModel>(
    FragmentDataSummaryBinding::inflate,
    SummaryDataViewModel::class
) {
    override fun setupViews() {
        val stationId = "1298491919449016030"
        childFragmentManager.beginTransaction()
            .replace(binding.monthlyChargeCard.id, MonthlyChargeCardFragment(stationId))
            .replace(binding.realTimeCard.id, RealTimeChargeCardFragment(stationId))
            .commit()
    }

    override fun setupObservers() {
        //
    }
}