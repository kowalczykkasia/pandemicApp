package com.android.pandemic.fighters

import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.repositories.VirusRepository
import com.android.pandemic.fighters.utils.location.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val provider: LocationProvider,
    private val virusRepository: VirusRepository
) : BaseViewModel() {

    init { //todo make it init {}
        provider.startLocationUpdates()
        launch(Dispatchers.IO) {
            virusRepository.updateData()
        }
    }

    override fun onCleared() {
        super.onCleared()
        provider.stopLocationUpdates()
    }
}