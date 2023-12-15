package com.gma.solarself.di

import com.gma.infrastructure.useCase.AppOpener
import com.gma.solarself.MainActivity
import com.gma.solarself.implementation.ConfigMonitoringCardViewModelImpl
import com.gma.solarself.implementation.ConfigViewModelImpl
import com.gma.solarself.implementation.DataAccessInputConfigUseCaseImpl
import com.gma.solarself.implementation.MonthlyChargeViewModelImpl
import com.gma.solarself.implementation.RealTimeChargeViewModelImpl
import com.gma.solarself.implementation.RegisterViewModelImpl
import com.gma.solarself.implementation.SolarDataViewModelImpl
import com.gma.solarself.implementation.SolarSelfViewModelImpl
import com.gma.solarself.implementation.SummaryDataViewModelImpl
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.useCase.DataAccessInputConfigUseCase
import com.gma.solarself.viewModel.ConfigMonitoringCardViewModel
import com.gma.solarself.viewModel.ConfigViewModel
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import com.gma.solarself.viewModel.RealTimeChargeViewModel
import com.gma.solarself.viewModel.RegisterViewModel
import com.gma.solarself.viewModel.SolarDataViewModel
import com.gma.solarself.viewModel.SolarSelfViewModel
import com.gma.solarself.viewModel.SummaryDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AppOpener> {
        MainActivity()
    }

    single<SolarSelfViewModel> {
        SolarSelfViewModelImpl(
            apiDataAccessUseCase = get()
        )
    }

    single<ConfigToolbarUseCase> { get<SolarSelfViewModel>() }

    viewModel<RegisterViewModel> {
        RegisterViewModelImpl(
            inputConfigUseCase = get(),
            validationConnection = get()
        )
    }

    viewModel<SolarDataViewModel> {
        SolarDataViewModelImpl(
            userStationDataUseCase = get(),
            configToolbarUseCase = get()
        )
    }

    viewModel<SummaryDataViewModel> {
        SummaryDataViewModelImpl(
            configStationUseCase = get()
        )
    }

    viewModel<MonthlyChargeViewModel> {
        MonthlyChargeViewModelImpl(
            stationMonthUseCase = get()
        )
    }

    viewModel<RealTimeChargeViewModel> {
        RealTimeChargeViewModelImpl(
            userStationDataUseCase = get()
        )
    }

    viewModel<ConfigViewModel> {
        ConfigViewModelImpl(
            configToolbarUseCase = get(),
            apiDataAccessUseCase = get()
        )
    }

    viewModel<ConfigMonitoringCardViewModel> {
        ConfigMonitoringCardViewModelImpl(
            userStationDataUseCase = get(),
            configStationUseCase = get()
        )
    }

    factory<DataAccessInputConfigUseCase> {
        DataAccessInputConfigUseCaseImpl()
    }
}