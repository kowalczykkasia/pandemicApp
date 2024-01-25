package com.android.pandemic.fighters.repositories

import com.android.pandemic.fighters.api.GeolocationService

class GeolocationRepository(
    private val geolocationService: GeolocationService
) {

    suspend fun getFormattedAddress(latitude: Double, longitude: Double) = geolocationService.getFormattedAddress("$latitude,$longitude")

    suspend fun getLatLongOfAddress(address: String) = geolocationService.getLatLongOfAddress(address)
}