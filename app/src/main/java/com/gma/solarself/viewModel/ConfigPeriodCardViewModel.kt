package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.ConfigDatePeriodModel
import com.gma.infrastructure.model.StationDataPage
import java.util.Date

abstract class ConfigPeriodCardViewModel : ExtendViewModel, ViewModel() {
    abstract val error: LiveData<Int>
    abstract val success: LiveData<Int>
    abstract val periodData: LiveData<ConfigDatePeriodModel?>
    abstract fun savePeriod(startDate: Date?, endDate: Date?, autoUpdatePeriod: Boolean)
    abstract fun clearPeriod()
}