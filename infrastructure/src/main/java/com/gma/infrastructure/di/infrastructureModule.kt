package com.gma.data_local.di

import com.gma.data_remote.di.dataRemoteModule
import com.gma.infrastructure.di.SaveRemoteApiDataAccess
import com.gma.infrastructure.implementation.ApiAccessValidationUseCaseimpl
import com.gma.infrastructure.implementation.ApiDataAccessUseCaseImpl
import com.gma.infrastructure.implementation.StationMonthUseCaseImpl
import com.gma.infrastructure.implementation.UserStationDataUseCaseImpl
import com.gma.infrastructure.implementation.WidgetConfigUseCaseImpl
import com.gma.infrastructure.useCase.ApiAccessValidationUseCase
import com.gma.infrastructure.useCase.ApiDataAccessUseCase
import com.gma.infrastructure.useCase.StationMonthUseCase
import com.gma.infrastructure.useCase.UserStationDataUseCase
import com.gma.infrastructure.useCase.WidgetConfigUseCase
import org.koin.dsl.module

val infrastructureModule = module {

    includes(
        dataLocalModule,
        dataRemoteModule
    )

    single {
        SaveRemoteApiDataAccess(get())
    }

    factory<ApiDataAccessUseCase> {
        ApiDataAccessUseCaseImpl(
            dataAccessUseCase = get(),
            saveRemoteApiDataAccess = get()
        )
    }

    factory<ApiAccessValidationUseCase> {
        ApiAccessValidationUseCaseimpl(
            apiDataAccessUseCase = get(),
            validateConnectionDataSource = get()
        )
    }

    factory<UserStationDataUseCase> {
        UserStationDataUseCaseImpl(
            userStationDataSource = get()
        )
    }

    factory<WidgetConfigUseCase> {
        WidgetConfigUseCaseImpl(
            configWidgetDao = get()
        )
    }

    factory<StationMonthUseCase> {
        StationMonthUseCaseImpl(
            stationMonthDataSource = get()
        )
    }
}