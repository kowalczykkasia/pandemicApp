package com.android.pandemic.fighters

import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.utils.location.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val provider: LocationProvider
) : BaseViewModel() {

    fun init() { //todo make it init {}
        provider.startLocationUpdates()
    }
}