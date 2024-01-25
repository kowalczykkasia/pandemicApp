package com.android.pandemic.fighters.utils.location

import android.location.Location

object LocationManager {

    private var currentLocation: Location? = null

    fun setCurrentLocation(location: Location) {
        currentLocation = location
    }

    fun getCurrentLocation(): Location? {
        return currentLocation
    }
}