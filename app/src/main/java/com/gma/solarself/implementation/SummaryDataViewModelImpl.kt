package com.gma.solarself.implementation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gma.infrastructure.useCase.ConfigStationUseCase
import com.gma.solarself.viewModel.SummaryDataViewModel
import kotlinx.coroutines.launch

class SummaryDataViewModelImpl : SummaryDataViewModel()