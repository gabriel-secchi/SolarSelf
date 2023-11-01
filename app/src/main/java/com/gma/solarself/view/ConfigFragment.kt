package com.gma.solarself.view

import com.gma.solarself.databinding.FragmentConfigBinding
import com.gma.solarself.viewModel.ConfigViewModel

class ConfigFragment : PatternFragment<FragmentConfigBinding, ConfigViewModel>(
    FragmentConfigBinding::inflate,
    ConfigViewModel::class
) {
    override fun setupViews() {
        viewModel.hideToolbarConfigButton()
        childFragmentManager.beginTransaction()
            .replace(binding.configWidgetCard.id, ConfigWidgetCardFragment())
            .commit()
    }

    override fun setupObservers() {
        //
    }
}