package com.gma.solarself.di

import com.gma.infrastructure.useCase.AppOpener
import com.gma.solarself.MainActivity
import com.gma.solarself.implementation.ConfigMonitoringCardViewModelImpl
import com.gma.solarself.implementation.ConfigPeriodCardViewModelImpl
import com.gma.solarself.implementation.ConfigViewModelImpl
import com.gma.solarself.implementation.DataAccessInputConfigUseCaseImpl
import com.gma.solarself.implementation.MonthlyChargeViewModelImpl
import com.gma.solarself.implementation.PatternViewModelImpl
import com.gma.solarself.implementation.PeriodChargeViewModelImpl
import com.gma.solarself.implementation.RealTimeChargeViewModelImpl
import com.gma.solarself.implementation.RegisterViewModelImpl
import com.gma.solarself.implementation.SolarDataViewModelImpl
import com.gma.solarself.implementation.SolarSelfViewModelImpl
import com.gma.solarself.implementation.UpdateDataMonitoringUseCaseImpl
import com.gma.solarself.useCase.ConfigToolbarUseCase
import com.gma.solarself.useCase.DataAccessInputConfigUseCase
import com.gma.solarself.useCase.UpdateDataMonitoringUseCase
import com.gma.solarself.viewModel.ConfigMonitoringCardViewModel
import com.gma.solarself.viewModel.ConfigPeriodCardViewModel
import com.gma.solarself.viewModel.ConfigViewModel
import com.gma.solarself.viewModel.MonthlyChargeViewModel
import com.gma.solarself.viewModel.PatternViewModel
import com.gma.solarself.viewModel.PeriodChargeViewModel
import com.gma.solarself.viewModel.RealTimeChargeViewModel
import com.gma.solarself.viewModel.RegisterViewModel
import com.gma.solarself.viewModel.SolarDataViewModel
import com.gma.solarself.viewModel.SolarSelfViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<AppOpener> {
        MainActivity()
    }

    single<ConfigToolbarUseCase> {
        get<SolarSelfViewModel>()
    }

    single<SolarSelfViewModel> {
        SolarSelfViewModelImpl(
            apiDataAccessUseCase = get()
        )
    }

    single<RealTimeChargeViewModel> {
        RealTimeChargeViewModelImpl(
            userStationDataUseCase = get()
        )
    }

    single<MonthlyChargeViewModel> {
        MonthlyChargeViewModelImpl(
            stationMonthUseCase = get()
        )
    }

    single<PeriodChargeViewModel> {
        PeriodChargeViewModelImpl(
            stationMonthUseCase = get(),
            configDatePeriodUseCase = get()
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
            configStationUseCase = get(),
            configToolbarUseCase = get(),
            updateDataMonitoring = get()
        )
    }

    viewModel<PatternViewModel> {
        PatternViewModelImpl()
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

    viewModel<ConfigPeriodCardViewModel> {
        ConfigPeriodCardViewModelImpl(
            configDatePeriodUseCase = get()
        )
    }

    factory<UpdateDataMonitoringUseCase> {
        UpdateDataMonitoringUseCaseImpl(
            realtimeChargeViewModel = get(),
            monthlyChargeViewModel = get(),
            periodChargeViewModel = get()
        )
    }

    factory<DataAccessInputConfigUseCase> {
        DataAccessInputConfigUseCaseImpl()
    }
}