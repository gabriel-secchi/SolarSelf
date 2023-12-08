package com.gma.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RemoteViews
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.useCase.AppOpener
import com.gma.widget.viewModel.WidgetViewModel
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.java.KoinJavaComponent.get
import org.koin.java.KoinJavaComponent.getKoin
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.reflect.KClass

class SolarSelfWidget : AppWidgetProvider() {
    private val viewModel: WidgetViewModel = getViewModel(getViewModelKclass())

    private fun getViewModelKclass(): KClass<WidgetViewModel> {
        return WidgetViewModel::class
    }

    private fun <T : ViewModel> getViewModel(
        clazz: KClass<T>,
        qualifier: Qualifier? = null,
        parameters: ParametersDefinition? = null
    ): T {
        return getKoin().get(clazz, qualifier, parameters)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        setupWidget(context)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        intent?.action?.let { action ->
            when (action) {
                ACTION_OPEN_APP -> {
                    openApp(context)
                }

                else -> {
                    setupWidget(context)
                }
            }
        }
    }

    private fun openApp(context: Context?) {
        val appOpener: AppOpener = get(AppOpener::class.java)
        appOpener.openApp(context)
    }

    private fun setupWidget(context: Context?) {
        viewModel.context = context
        setupObservers()
        loadData()
    }

    private fun loadData() {
        viewModel.fetchUserStationData()
    }

    private fun setupObservers() {
        viewModel.userStationData.removeObserver(::setupData)
        viewModel.loading.removeObserver(::setupLoading)

        viewModel.userStationData.observeForever(::setupData)
        viewModel.loading.observeForever(::setupLoading)
    }

    private fun validateConfiguration(
        ctx: Context,
        views: RemoteViews
    ) {
        if (viewModel.notConfigured) {
            setupComponentsClickListener(ctx, views)
            openApp(viewModel.context)
        }
    }

    private fun setupLoading(isVisible: Boolean) {
        updateDataWidget(viewModel.context) { ctx, views ->
            views.setViewVisibility(R.id.loading_icon, if (isVisible) View.VISIBLE else View.GONE)
            views.setViewVisibility(R.id.btn_update, if (isVisible) View.GONE else View.VISIBLE)
            if (!isVisible) {
                setupComponentsClickListener(ctx, views)
                validateConfiguration(ctx, views)
            }
        }
    }

    private fun setupData(data: UserStationModel?) {
        updateDataWidget(viewModel.context) { _, views ->
            setupDayEnergy(views, data?.dayEnergy)
            setupPower(views, data?.power)
            setLastUpdate(views)
        }
    }

    private fun getString(stringId: Int): String {
        return viewModel.context?.getString(stringId) ?: ""
    }

    private fun updateDataWidget(
        context: Context?,
        callback: (context: Context, rmViews: RemoteViews) -> Unit
    ) {
        context?.let { ctx ->
            val appWidgetManager = AppWidgetManager.getInstance(ctx)
            appWidgetManager.getAppWidgetIds(ComponentName(ctx, javaClass))
                .forEach { appWidgetId ->
                    val views = RemoteViews(ctx.packageName, R.layout.solar_self_widget)
                    callback(ctx, views)
                    appWidgetManager.updateAppWidget(appWidgetId, views)
                }
        }
    }

    private fun setupDayEnergy(views: RemoteViews, dayEnergy: Float?) {
        dayEnergy?.let {
            var kW = getString(R.string.zero)
            var w = getString(R.string.dot_zero)
            try {
                val parts = it.toString().trim().split(".")
                kW = parts.first()
                w = getString(R.string.dot).plus(parts.last())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

            views.setTextViewText(R.id.day_energy_p1, kW)
            views.setTextViewText(R.id.day_energy_p2, w)
        }
    }

    private fun setupPower(views: RemoteViews, power: Float?) {
        power?.let {
            var kW = getString(R.string.zero)
            var w: String? = null
            if (it < 1) {
                kW = (it * 1000).toInt().toString()
            } else {
                try {
                    val parts = it.toString().trim().split(".")
                    kW = parts.first()
                    w = getString(R.string.dot).plus(parts.last())
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            }

            views.setTextViewText(R.id.power_p1, kW)
            views.setTextViewText(R.id.power_p2, w ?: "")
            views.setViewVisibility(
                R.id.power_p2,
                if (w.isNullOrEmpty()) View.GONE else View.VISIBLE
            )
            views.setTextViewText(R.id.power_metric, if (w.isNullOrEmpty()) "W" else "kW")
        }
    }

    private fun setLastUpdate(views: RemoteViews) {
        val format = SimpleDateFormat(HOUR_MASK, Locale.getDefault())
        val formatedTime = format.format(Date())
        views.setTextViewText(R.id.txt_last_update, formatedTime)
    }

    private fun setupComponentsClickListener(
        context: Context,
        views: RemoteViews
    ) {
        views.setOnClickPendingIntent(
            R.id.btn_update,
            buildPendingIntent(context, ACTION_CLICK_UPDATE_DATA)
        )
        views.setOnClickPendingIntent(R.id.widget, buildPendingIntent(context, ACTION_OPEN_APP))
    }

    private fun buildPendingIntent(
        context: Context,
        intentAction: String,
    ): PendingIntent? {
        Intent(context, javaClass).apply {
            action = intentAction
            return PendingIntent.getBroadcast(
                context, 0, this,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

    companion object {
        private const val HOUR_MASK = "HH:mm"
        private const val ACTION_CLICK_UPDATE_DATA = "actionClickUpdateData"
        private const val ACTION_OPEN_APP = "actionOpenApp"
    }
}