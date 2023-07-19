package com.gma.solarself.view

import androidx.appcompat.app.AppCompatActivity
import com.gma.infrastructure.model.UserStationModel
import com.gma.solarself.databinding.FragmentDataBinding
import com.gma.solarself.viewModel.SolarDataViewModel

class DataFragment : PatternFragment<FragmentDataBinding, SolarDataViewModel>(
    FragmentDataBinding::inflate,
    SolarDataViewModel::class
) {
    override fun setupViews() {
        disableBackPressed()
    }

    override fun setupObservers() {
        viewModel.loading.observe(requireActivity(), ::displayLoading)
        viewModel.stationData.observe(requireActivity(), ::setupStationData)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setHomeButtonEnabled(false) // disable the button
            setDisplayHomeAsUpEnabled(false) // remove the left caret
            setDisplayShowHomeEnabled(false) // remove the icon
        }
    }

    private fun setupStationData(station: UserStationModel?) {
        binding.textviewSecond.text = station?.id ?: "no data"
        binding.tvPower.text = "${station?.power} W"
        binding.tvEnergy.text = "${station?.dayEnergy} KWh"
    }
}