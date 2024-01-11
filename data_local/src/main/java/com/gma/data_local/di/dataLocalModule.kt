package com.gma.data_local.di

import com.gma.data_local.dao.ConfigApiDao
import com.gma.data_local.dao.ConfigAppDao
import com.gma.data_local.dao.ConfigPeriodDao
import com.gma.data_local.dao.ConfigWidgetDao
import com.gma.data_local.implementation.ConfigMonitoredStationUseCaseImpl
import com.gma.data_local.implementation.ConfigPeriodUseCaseImpl
import com.gma.data_local.implementation.ConfigWidgetUseCaseImpl
import com.gma.data_local.implementation.DataAccessUseCaseImpl
import com.gma.data_local.implementationDao.ConfigApiDaoImpl
import com.gma.data_local.implementationDao.ConfigAppDaoImpl
import com.gma.data_local.implementationDao.ConfigPeriodDaoImpl
import com.gma.data_local.implementationDao.ConfigWidgetDaoImpl
import com.gma.data_local.realmDatabase.openDatabase
import com.gma.data_local.useCase.ConfigMonitoredStationUseCase
import com.gma.data_local.useCase.ConfigPeriodUseCase
import com.gma.data_local.useCase.ConfigWidgetUseCase
import com.gma.data_local.useCase.DataAccessUseCase
import org.koin.dsl.module

val dataLocalModule = module {

    single { openDatabase() }

    single<ConfigApiDao> {
        ConfigApiDaoImpl(
            database = get()
        )
    }

    single<ConfigAppDao> {
        ConfigAppDaoImpl(
            database = get()
        )
    }

    single<ConfigWidgetDao> {
        ConfigWidgetDaoImpl(
            database = get()
        )
    }

    single<ConfigPeriodDao> {
        ConfigPeriodDaoImpl(
            database = get()
        )
    }

    factory<DataAccessUseCase> {
        DataAccessUseCaseImpl(
            configApiDao = get()
        )
    }

    factory<ConfigWidgetUseCase> {
        ConfigWidgetUseCaseImpl(
            configAppDao = get()
        )
    }

    single<ConfigMonitoredStationUseCase> {
        ConfigMonitoredStationUseCaseImpl(
            configDao = get()
        )
    }

    single<ConfigPeriodUseCase> {
        ConfigPeriodUseCaseImpl(
            configDao = get()
        )
    }
}