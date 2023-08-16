package com.gma.solarself.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.gma.solarself.R
import org.koin.android.ext.android.getKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import kotlin.reflect.KClass

abstract class PatternFragment<VB : ViewBinding, VM : ViewModel>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val viewModelClass: KClass<VM>
) :
    Fragment() {
    private var cancelBackPressed = false

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected lateinit var viewModel: VM

    protected abstract fun setupViews()
    protected abstract fun setupObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!cancelBackPressed) {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(inflater, container, false)
        viewModel = getViewModel(viewModelClass)

        return binding.root
    }

    protected fun displayLoading(isVisible: Boolean) {
        view?.let {
            val loading = it.findViewById<RelativeLayout>(R.id.loading)
            loading.isVisible = isVisible
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun <T : ViewModel> getViewModel(
        clazz: KClass<T>,
        qualifier: Qualifier? = null,
        parameters: ParametersDefinition? = null
    ): T {
        return getKoin().get(clazz, qualifier, parameters)
    }

    protected fun disableBackPressed() {
        cancelBackPressed = true
    }

    private fun getAppCompatActivity() = activity as AppCompatActivity
    protected fun getActionBar() = getAppCompatActivity().supportActionBar
    protected fun hideActionBar() = getActionBar()?.hide()
}