package com.gma.solarself.view.config

import android.app.AlertDialog
import android.content.Intent
import com.gma.solarself.MainActivity
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentConfigBinding
import com.gma.solarself.view.PatternFragment
import com.gma.solarself.view.components.CustomSnackBar
import com.gma.solarself.viewModel.ConfigViewModel

class ConfigFragment : PatternFragment<FragmentConfigBinding, ConfigViewModel>(
    FragmentConfigBinding::inflate,
    ConfigViewModel::class
) {
    override fun setupViews() {
        viewModel.hideToolbarConfigButton()
        childFragmentManager.beginTransaction()
            .replace(binding.configWidgetCard.id, ConfigMonitoringCardFragment())
            .commit()
        binding.btnLogoff.setOnClickListener { setupLogoff() }
    }

    override fun setupObservers() {
        viewModel.loggedOut.observe(requireActivity(), ::loggedOut)
        viewModel.loading.observe(requireActivity(), ::displayLoading)
    }

    private fun loggedOut(success: Boolean) {
        if (success) {
            //requireActivity().finish()
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            requireActivity().startActivity(intent)
            //requireActivity().finishAffinity()
            Runtime.getRuntime().exit(0)
        } else {
            CustomSnackBar
                .make(view, R.string.config_screen_station_monitored_updated)
                .setSuccessStyle()
                .show()
        }
    }

    private fun setupLogoff() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.logoff)
            .setMessage(R.string.config_screen_logoff_alert)
            .setPositiveButton(R.string.ok) { _, _ ->
                viewModel.logOff()
            }
            .setNegativeButton(R.string.cancel) { i, _ -> i?.cancel() }
            .create()
            .show()
    }
}