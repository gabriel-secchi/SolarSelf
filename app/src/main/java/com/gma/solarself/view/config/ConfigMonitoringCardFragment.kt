package com.gma.solarself.view.config

import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.gma.infrastructure.model.StationDataPage
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentConfigMonitoringCardBinding
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.view.components.CustomSnackBar
import com.gma.solarself.viewModel.ConfigMonitoringCardViewModel

class ConfigMonitoringCardFragment :
    PatternFragment<FragmentConfigMonitoringCardBinding, ConfigMonitoringCardViewModel>(
        FragmentConfigMonitoringCardBinding::inflate,
        ConfigMonitoringCardViewModel::class
    ) {
    private var lastSelectionStation: String? = null

    private val adapterStationList: ArrayAdapter<String> by lazy {
        ArrayAdapter(
            requireContext(),
            R.layout.adapter_item_station_list,
            mutableListOf<String>()
        )
    }

    override fun setupViews() {
        setupWidgetConfigStationList()
        setupWidgetConfigUnselectStation()
    }

    private fun setupWidgetConfigStationList() {
        binding.stationList.apply {
            setAdapter(adapterStationList)
            setOnDismissListener {
                clearFocus()
            }
            setOnItemClickListener { _, _, i, _ ->
                lastSelectionStation = adapterStationList.getItem(i).toString()
                saveWidgetConfig()
            }
        }
    }

    private fun saveWidgetConfig() {
        viewModel.saveWidgetConfig(
            lastSelectionStation
        )
    }

    private fun setupWidgetConfigUnselectStation() {
        binding.btnClrSelection.setOnClickListener {
            lastSelectionStation = null
            saveWidgetConfig()
//            viewModel.deleteWidgetConfig()
            binding.stationList.text = null
        }
    }

    override fun setupObservers() {
        viewModel.loading.observe(requireActivity(), ::setupLoadingShimmer)
        viewModel.stationList.observe(requireActivity(), ::setupStationList)
        viewModel.stationIdConfigured.observe(requireActivity(), ::setupWidgetConfig)
        viewModel.configUpdated.observe(requireActivity()) {
            CustomSnackBar
                .make(view, R.string.config_screen_widget_updated)
                .setSuccessStyle()
                .show()
            lastSelectionStation = binding.stationList.text.toString()
            setLastSelectedStation()
        }
        viewModel.error.observe(requireActivity()) { errorMessage ->
            CustomSnackBar
                .make(view, errorMessage)
                .setErrorStyle()
                .show()
            setLastSelectedStation()
        }
    }

    private fun setupLoadingShimmer(isVisible: Boolean) {
        if (isVisible)
            binding.root.showShimmer(true)
        else {
            binding.root.stopShimmer()
            binding.root.hideShimmer()
        }
    }

    private fun setupWidgetConfig(monitoredStationId: String?) {
        lastSelectionStation = monitoredStationId
        setLastSelectedStation()
    }

    private fun getAdapterItemValue(selectedId: String): String? {
        val adapterPosition = adapterStationList.getPosition(selectedId)
        if (adapterPosition < 0)
            return null

        return adapterStationList.getItem(adapterPosition)
    }

    private fun setupStationList(stationList: List<StationDataPage>) {
        adapterStationList.clear()
        adapterStationList.addAll(
            stationList.map { it.id }
        )
    }

    private fun setLastSelectedStation() {
        if (lastSelectionStation.isNullOrBlank()) {
            binding.stationList.text = null
            binding.btnClrSelection.isVisible = false
        } else {
            binding.stationList.setText(getAdapterItemValue(lastSelectionStation!!))
            binding.btnClrSelection.isVisible = true
        }
    }
}