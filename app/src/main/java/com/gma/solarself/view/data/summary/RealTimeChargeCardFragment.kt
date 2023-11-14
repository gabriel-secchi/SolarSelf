package com.gma.solarself.view.data.summary

import com.gma.infrastructure.model.UserStationModel
import com.gma.solarself.databinding.FragmentRealTimeChargeCardBinding
import com.gma.solarself.utils.toEnergyCharge
import com.gma.solarself.utils.toPowerCharge
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.viewModel.RealTimeChargeViewModel

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
        viewModel.stationData.observe(requireActivity(), ::fetchStationData)
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

    private fun fetchStationData(userStationModel: UserStationModel?) {
        userStationModel?.let {
            binding.tvCharge.text = it.dayEnergy.toEnergyCharge()
            binding.tvPower.text = it.power.toPowerCharge()
        }
    }
}