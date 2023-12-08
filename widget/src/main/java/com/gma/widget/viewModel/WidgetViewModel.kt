package com.gma.widget.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gma.infrastructure.model.UserStationModel
import com.gma.infrastructure.model.WidgetConfig

abstract class WidgetViewModel: ViewModel() {
    var context: Context? = null
    abstract val userStationData: LiveData<UserStationModel?>
    abstract val loading: LiveData<Boolean>
    var notConfigured: Boolean = false

    abstract fun fetchUserStationData()
}