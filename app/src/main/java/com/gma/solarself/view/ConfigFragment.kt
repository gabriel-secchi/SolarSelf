package com.gma.solarself.view

import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import com.gma.infrastructure.model.StationDataPage
import com.gma.infrastructure.model.WidgetConfig
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentConfigBinding
import com.gma.solarself.view.components.CustomSnackBar
import com.gma.solarself.viewModel.ConfigViewModel

class ConfigFragment : PatternFragment<FragmentConfigBinding, ConfigViewModel>(
    FragmentConfigBinding::inflate,
    ConfigViewModel::class
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
        viewModel.hideToolbarConfigButton()
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
                val selectedStation = adapterStationList.getItem(i).toString()
                viewModel.saveWidgetConfig(selectedStation)
            }
        }
    }

    private fun setupWidgetConfigUnselectStation() {
        binding.btnClrSelection.setOnClickListener {
            viewModel.deleteWidgetConfig()
            binding.stationList.text = null
        }
    }

    override fun setupObservers() {
        viewModel.loading.observe(requireActivity(), ::displayLoading)
        viewModel.stationList.observe(requireActivity(), ::setupStationList)
        viewModel.widgetConfig.observe(requireActivity(), ::setupWidgetConfig)
        viewModel.widgedConfigUpdated.observe(requireActivity()) {
            CustomSnackBar
                .make(view, R.string.config_screen_widget_updated)
                .setSuccessStyle(requireActivity())
                .show()
            lastSelectionStation = binding.stationList.text.toString()
            setLastSelectedStation()
        }
        viewModel.error.observe(requireActivity()) { errorMessage ->
            CustomSnackBar
                .make(view, errorMessage)
                .setErrorStyle(requireActivity())
                .show()
            setLastSelectedStation()
        }
    }

    private fun setupWidgetConfig(widgetConfig: WidgetConfig?) {
        lastSelectionStation = widgetConfig?.monitoredStationId
        setLastSelectedStation()
    }

    private fun getAdapterItemValue(selectedId: String): String? {
        val adapterPosition = adapterStationList.getPosition(selectedId)
        if(adapterPosition < 0)
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
        if(lastSelectionStation.isNullOrBlank()) {
            binding.stationList.text = null
            binding.btnClrSelection.isVisible = false
        }
        else {
            binding.stationList.setText(getAdapterItemValue(lastSelectionStation!!))
            binding.btnClrSelection.isVisible = true
        }
    }
}