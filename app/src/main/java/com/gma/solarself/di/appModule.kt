package com.gma.solarself.di

import com.gma.infrastructure.useCase.AppOpener
import com.gma.solarself.MainActivity
import com.gma.solarself.implementation.DataAccessInputConfigUseCaseImpl
import com.gma.solarself.implementation.RegisterViewModelImpl
import com.gma.solarself.implementation.SolarDataViewModelImpl
import com.gma.solarself.implementation.SolarSelfViewModelImpl
import com.gma.solarself.useCase.DataAccessInputConfigUseCase
import com.gma.solarself.viewModel.RegisterViewModel
import com.gma.solarself.viewModel.SolarDataViewModel
import com.gma.solarself.viewModel.SolarSelfViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AppOpener> {
        MainActivity()
    }

    viewModel<SolarSelfViewModel> {
        SolarSelfViewModelImpl(
            apiDataAccessUseCase = get()
        )
    }

    viewModel<RegisterViewModel> {
        RegisterViewModelImpl(
            inputConfigUseCase = get(),
            validationConnection = get()
        )
    }

    viewModel<SolarDataViewModel> {
        SolarDataViewModelImpl(
            userStationDataUseCase = get()
        )
    }

    factory<DataAccessInputConfigUseCase> {
        DataAccessInputConfigUseCaseImpl()
    }
}