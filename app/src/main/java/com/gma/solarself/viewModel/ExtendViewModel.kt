package com.gma.solarself.viewModel

import androidx.lifecycle.LiveData

interface ExtendViewModel {
    val loading: LiveData<Boolean>
}