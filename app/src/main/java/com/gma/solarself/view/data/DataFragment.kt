package com.gma.solarself.view.data

import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentDataBinding
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.view.components.CustomSnackBar
import com.gma.solarself.view.data.body.DataBodyFragment
import com.gma.solarself.view.data.summary.DataSummaryFragment
import com.gma.solarself.viewModel.SolarDataViewModel

class DataFragment : PatternFragment<FragmentDataBinding, SolarDataViewModel>(
    FragmentDataBinding::inflate,
    SolarDataViewModel::class
) {
    private var monitoredStationId: String? = null
    override fun setupViews() {
        viewModel.setupMonitoredStation()
        setupSwipeRefresh()
        setupBackPressedButton()
    }

    override fun setupObservers() {
        viewModel.loading.observe(requireActivity(), ::displayLoading)
        viewModel.monitoredStationId.observe(requireActivity(), ::setupMonitoredStation)
    }

    override fun displayLoading(isVisible: Boolean) {
        super.displayLoading(isVisible)
        if (!isVisible)
            binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.setupMonitoredStation()
        }
    }

    private fun setupChildFragmentManager() {
        binding.noStationConfigured.isVisible = monitoredStationId.isNullOrBlank()
        if (monitoredStationId.isNullOrBlank()) {
            childFragmentManager.apply {
                fragments.forEach {
                    beginTransaction()
                        .remove(it)
                        .commit()
                }
            }
        } else {
            childFragmentManager.beginTransaction()
                .replace(binding.stationSummaryList.id, DataSummaryFragment(monitoredStationId!!))
                .replace(binding.stationBodyList.id, DataBodyFragment(monitoredStationId!!))
                .commit()
        }
    }

    private fun setupBackPressedButton() {
        setBackPressedCallback {
            CustomSnackBar
                .make(view, R.string.data_screen_close_app)
                .show()
            setBackPressedCallback {
                requireActivity().finish()
            }
            Handler(Looper.getMainLooper()).postDelayed({
                setupBackPressedButton()
            }, BACK_PRESSED_RESET_TIMEOUT)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setHomeButtonEnabled(false) // disable the button
            setDisplayHomeAsUpEnabled(false) // remove the left caret
            setDisplayShowHomeEnabled(false) // remove the icon
        }
        viewModel.showToolbarConfigButton()
    }

    private fun setupMonitoredStation(stationId: String?) {
        monitoredStationId = stationId
        setupChildFragmentManager()
    }

    private companion object {
        const val BACK_PRESSED_RESET_TIMEOUT = 5000L
    }
}