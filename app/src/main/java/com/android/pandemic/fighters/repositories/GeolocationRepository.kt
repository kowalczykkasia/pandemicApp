package com.android.pandemic.fighters.repositories

import com.android.pandemic.fighters.api.GeolocationService
import com.android.pandemic.fighters.base.handleApiResponse

class GeolocationRepository(
    private val geolocationService: GeolocationService
) {

    suspend fun getFormattedAddress(latitude: Double, longitude: Double) =
        handleApiResponse { geolocationService.getFormattedAddress("$latitude,$longitude") }
}