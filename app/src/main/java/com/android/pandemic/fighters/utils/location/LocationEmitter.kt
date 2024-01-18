package com.android.pandemic.fighters.utils.location

import android.location.Location
import kotlinx.coroutines.flow.MutableSharedFlow

class LocationEmitter {

    private val currentLocation = MutableSharedFlow<Location>()

    suspend fun setCurrentLocation(location: Location) {
        currentLocation.emit(location)
    }

    fun getCurrentLocation() = currentLocation
}