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
            .replace(binding.configStationCard.id, ConfigMonitoringCardFragment())
            .replace(binding.configPeriodCard.id, ConfigPeriodCardFragment())
            .commit()
        binding.btnLogoff.setOnClickListener { setupLogoff() }
    }

    override fun setupObservers() {
        viewModel.loggedOut.observe(requireActivity(), ::loggedOut)
        viewModel.loading.observe(requireActivity(), ::displayLoading)
    }

    private fun loggedOut(success: Boolean) {
        if (success) {
            restartApp()
        } else {
            CustomSnackBar
                .make(view, R.string.config_screen_logout_error)
                .setErrorStyle()
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

    private fun restartApp() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        requireActivity().startActivity(intent)
        Runtime.getRuntime().exit(0)
    }
}