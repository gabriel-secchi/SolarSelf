package com.gma.solarself.view

import androidx.navigation.fragment.findNavController
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentRegisterBinding
import com.gma.solarself.model.DataAccessInputConfig
import com.gma.solarself.viewModel.RegisterViewModel

class RegisterFragment : PatternFragment<FragmentRegisterBinding, RegisterViewModel>(
    FragmentRegisterBinding::inflate,
    RegisterViewModel::class
) {

    override fun setupViews() {
        hideActionBar()
        setupSaveButton()
        disableBackPressed()
    }

    override fun setupObservers() {
        viewModel.loading.observe(requireActivity(), ::displayLoading)
        viewModel.inputsConfig.observe(requireActivity(), ::setupFields)
        viewModel.successDataSaved.observe(requireActivity(), ::goToStationFragment)
    }

    private fun setupSaveButton() {
        binding.buttonFirst.setOnClickListener {
            try {
                registerValidation()
                saveFormData()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun saveFormData() {
        viewModel.saveDataAccess(
            RegisterViewModel.SaveApiParams(
                url = binding.apiUrlInput.text,
                keyId = binding.keyIdInput.text,
                keySecret = binding.keySecretInput.text
            )
        )
    }

    private fun setupFields(config: DataAccessInputConfig) {
        binding.apiUrlInput.config = config.urlInput
        binding.keyIdInput.config = config.keyIdInput
        binding.keySecretInput.config = config.keySecretInput
    }

    private fun registerValidation() {
        var formIsValid = true

        formIsValid = binding.apiUrlInput.isValid && formIsValid
        formIsValid = binding.keyIdInput.isValid && formIsValid
        formIsValid = binding.keySecretInput.isValid && formIsValid

        if (!formIsValid)
            throw Exception("")
    }

    private fun goToStationFragment(saveSuccess: Boolean) {
        if (saveSuccess)
            findNavController().navigate(R.id.action_Register_To_Data)
    }
}