package com.gma.solarself.view.data.summary

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.gma.solarself.databinding.FragmentDataSummaryBinding
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.viewModel.SummaryDataViewModel

class DataSummaryFragment : PatternFragment<FragmentDataSummaryBinding, SummaryDataViewModel>(
    FragmentDataSummaryBinding::inflate,
    SummaryDataViewModel::class
) {
    override fun setupViews() {
        viewModel.setupMonitoredStation()
    }

    override fun setupObservers() {
        viewModel.monitoredStationId.observe(requireActivity(), ::setupSummaryComponents)
    }

    fun setupSummaryComponents(monitoredStationId: String) {
        view?.post {
            childFragmentManager.beginTransaction()
                .replace(binding.monthlyChargeCard.id, MonthlyChargeCardFragment(monitoredStationId))
                .replace(binding.realTimeCard.id, RealTimeChargeCardFragment(monitoredStationId))
                .commit()
        }
    }
}