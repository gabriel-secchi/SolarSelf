package com.gma.solarself.application

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.gma.widget.WidgetUpdateWorker
import java.util.concurrent.TimeUnit

open class WidgetWorkers(
    val context: Context
) {

    open fun scheduleWidgetWorker() {
        val workRequest = PeriodicWorkRequestBuilder<WidgetUpdateWorker>(
            5L, TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build()
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private companion object {
        private const val WORK_NAME = "update_widget_work"
    }
}