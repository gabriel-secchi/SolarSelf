package com.gma.widget.di

import com.gma.widget.implementation.WidgetViewModelImpl
import com.gma.widget.viewModel.WidgetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val widgetModule = module {

    viewModel<WidgetViewModel> {
        WidgetViewModelImpl(
            apiDataAccessUseCase = get(),
            userStationDataUseCase = get(),
            widgetConfigUseCase = get()
        )
    }

}