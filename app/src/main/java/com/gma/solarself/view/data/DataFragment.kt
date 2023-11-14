package com.gma.solarself.view.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gma.infrastructure.model.UserStationModel
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentDataBinding
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.view.components.CustomSnackBar
import com.gma.solarself.view.data.summary.DataSummaryFragment
import com.gma.solarself.viewModel.SolarDataViewModel

class DataFragment : PatternFragment<FragmentDataBinding, SolarDataViewModel>(
    FragmentDataBinding::inflate,
    SolarDataViewModel::class
) {
    override fun setupViews() {
        setupChildFragmentManager()
        setupBackPressedButton()
    }

    override fun setupObservers() {
        viewModel.loading.observe(requireActivity(), ::displayLoading)
        viewModel.stationData.observe(requireActivity(), ::setupStationData)
    }

    private fun setupChildFragmentManager() {
        childFragmentManager.beginTransaction()
            .replace(binding.stationSummaryList.id, DataSummaryFragment())
            .commit()
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

    private fun setupStationData(station: UserStationModel?) {
        try {
            binding.textviewSecond.text = station?.id ?: "no data"
            binding.tvPower.text = station?.power.toString().plus(" W")
            binding.tvEnergy.text = station?.dayEnergy.toString().plus(" KWh")
        } catch (ex: Exception) {
            val message = ex.message
            Log.e("setupDataStation", message, ex)
            ex.printStackTrace()
        }
    }

    private companion object {
        const val BACK_PRESSED_RESET_TIMEOUT = 5000L
    }
}