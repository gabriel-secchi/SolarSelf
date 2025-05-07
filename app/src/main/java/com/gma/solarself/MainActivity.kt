package com.gma.solarself

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.gma.infrastructure.useCase.AppOpener
import com.gma.solarself.databinding.ActivityMainBinding
import com.gma.solarself.utils.safeNavigationTo
import com.gma.solarself.utils.setSafeOnClickListener
import com.gma.solarself.viewModel.SolarSelfViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), AppOpener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val viewmodel: SolarSelfViewModel by viewModel()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupNavController()
        setupView()
        setupObservers()
        viewmodel.validateRegistration()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupNavController() {
        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun setupView() {
        binding.configButton.setSafeOnClickListener {
            navController.safeNavigationTo(R.id.action_Data_To_Config)
        }
    }

    private fun setupObservers() {
        viewmodel.openData.observe(this) { registred ->
            var action = R.id.action_Splash_To_Register
            if (registred)
                action = R.id.action_Splash_To_Data


            navController.safeNavigationTo(action)
        }

        viewmodel.displayToolbarConfigButton.observe(this) { isVisible ->
            binding.configButton.isVisible = isVisible
        }
    }

    override fun openApp(context: Context?) {
        context?.let { ctx ->
            try {
                val openAppIntent =
                    ctx.packageManager?.getLaunchIntentForPackage("com.gma.solarself")
                openAppIntent?.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                ctx.startActivity(openAppIntent)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

}