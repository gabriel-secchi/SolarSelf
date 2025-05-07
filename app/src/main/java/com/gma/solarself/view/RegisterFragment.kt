package com.gma.solarself.view

import android.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.gma.solarself.R
import com.gma.solarself.databinding.FragmentRegisterBinding
import com.gma.solarself.model.DataAccessInputConfig
import com.gma.solarself.utils.safeNavigationTo
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
        viewModel.errorMessage.observe(requireActivity(), ::displayError)
    }

    private fun setupSaveButton() {
        binding.btnLogin.setOnClickListener {
            try {
                registerValidation()
                saveFormData()
            } catch (ex: Exception) {
                ex.printStackTrace()
                displayError(ex.message ?: "no message")
            }
        }
    }

    private fun displayError(errorMessage: String, title: String = "Error", onSuccess: (() -> Unit)? = null) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(errorMessage)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
                onSuccess?.let { function ->
                    function.invoke()
                }
            }
            .create()
            .show()
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
            throw Exception("For is invalid")
    }

    private fun goToStationFragment(saveSuccess: Boolean) {
        if (saveSuccess)
            findNavController().safeNavigationTo(R.id.action_Register_To_Data)
    }
}