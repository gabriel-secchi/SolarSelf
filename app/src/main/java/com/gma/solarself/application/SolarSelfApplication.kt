package com.gma.solarself.application

import android.app.Application
import com.gma.data_local.di.infrastructureModule
import com.gma.solarself.di.appModule
import com.gma.widget.di.widgetModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SolarSelfApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SolarSelfApplication)
            modules(
                appModule,
                infrastructureModule,
                widgetModule
            )
        }
    }
}