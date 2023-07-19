package com.gma.data_local.di

import com.gma.data_local.dao.ConfigApiDao
import com.gma.data_local.implementationDao.ConfigApiDaoImpl
import com.gma.data_local.implementation.DataAccessUseCaseImpl
import com.gma.data_local.implementationDao.CustomDaoImpl
import com.gma.data_local.realmDatabase.openDatabase
import com.gma.data_local.useCase.DataAccessUseCase
import org.koin.dsl.module

val dataLocalModule = module {

    single { openDatabase() }

    single<ConfigApiDao> {
        ConfigApiDaoImpl(
            database = get()
        )
    }

    factory<DataAccessUseCase> {
        DataAccessUseCaseImpl(
            customPreferences = get()
        )
    }
}