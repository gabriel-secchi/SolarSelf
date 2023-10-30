package com.gma.widget.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.UserStationModel

abstract class WidgetViewModel: ViewModel() {
    var context: Context? = null
    abstract val userStationData: LiveData<UserStationModel?>
    abstract val loading: LiveData<Boolean>
    var notConfigured: Boolean = false
    var monitoredStationId: String? = null

    abstract fun fetchUserStationData()
}