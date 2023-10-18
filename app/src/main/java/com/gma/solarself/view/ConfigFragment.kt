package com.gma.solarself.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentConfigBinding
import com.gma.solarself.viewModel.ConfigViewModel

class ConfigFragment : PatternFragment<FragmentConfigBinding, ConfigViewModel>(
    FragmentConfigBinding::inflate,
    ConfigViewModel::class
) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val menuHost: MenuHost = requireActivity()
        //menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun setupViews() {
        viewModel.hideToolbarConfigButton()
        //disableBackPressed()
    }

    override fun setupObservers() {
        viewModel.loading.observe(requireActivity(), ::displayLoading)
        //viewModel.stationData.observe(requireActivity(), ::setupStationData)
    }

    override fun onResume() {
        super.onResume()
        /*(activity as AppCompatActivity).supportActionBar?.apply {
            show()
            setHomeButtonEnabled(false) // disable the button
            setDisplayHomeAsUpEnabled(false) // remove the left caret
            setDisplayShowHomeEnabled(false) // remove the icon
        }*/
    }

    /*override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
     //  menu.removeItem(R.id.action_settings)
    }*/

    /*override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }*/

    /*private fun setupStationData(station: UserStationModel?) {
        binding.textviewSecond.text = station?.id ?: "no data"
        binding.tvPower.text = "${station?.power} W"
        binding.tvEnergy.text = "${station?.dayEnergy} KWh"
    }*/
}