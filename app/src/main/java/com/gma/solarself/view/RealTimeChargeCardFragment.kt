package com.gma.solarself.view

import android.util.Log
import com.gma.infrastructure.model.UserStationModel
import com.gma.solarself.databinding.FragmentDataSummaryBinding
import com.gma.solarself.databinding.FragmentMonthlyChargeCardBinding
import com.gma.solarself.databinding.FragmentRealTimeChargeCardBinding
import com.gma.solarself.model.MonthlyChargeModel
import com.gma.solarself.utils.twoDecimalPlaces
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import com.gma.solarself.viewModel.RealTimeChargeViewModel
import com.gma.solarself.viewModel.SummaryDataViewModel
import java.util.Timer
import java.util.TimerTask

class RealTimeChargeCardFragment(
    val stationId: String
) : PatternFragment<FragmentRealTimeChargeCardBinding, RealTimeChargeViewModel>(
    FragmentRealTimeChargeCardBinding::inflate,
    RealTimeChargeViewModel::class
) {
    override fun setupViews() {
        viewModel.fetchStationData(stationId)
    }

    override fun onResume() {
        super.onResume()
        viewModel.setRealTimeFetching(true)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setRealTimeFetching(false)
    }

    override fun setupObservers() {
        viewModel.stationData.observe(requireActivity(), ::setupMonthlySummary)
        viewModel.loading.observe(requireActivity()) { isVisible ->
            if (isVisible) {
                binding.root.showShimmer(true)
            }
            else {
                binding.root.stopShimmer()
                binding.root.hideShimmer()
            }
        }
    }

    private fun setupMonthlySummary(userStationModel: UserStationModel?) {
        userStationModel?.let {
            binding.tvCharge.text = it.dayEnergy.toString().plus(" KWh")
            binding.tvPower.text = it.power.toString().plus(" W")
        }
    }
}