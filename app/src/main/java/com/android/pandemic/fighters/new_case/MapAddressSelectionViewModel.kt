package com.android.pandemic.fighters.new_case

import android.location.Location
import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.base.mutableSharedFlow
import com.android.pandemic.fighters.new_case.models.FormattedAddressResponseModel
import com.android.pandemic.fighters.new_case.models.LatLngResponseModel
import com.android.pandemic.fighters.repositories.GeolocationRepository
import com.android.pandemic.fighters.utils.location.LocationEmitter
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class MapAddressSelectionViewModel @Inject constructor(
    private val geolocationRepository: GeolocationRepository,
    private val locationEmitter: LocationEmitter
) : BaseViewModel() {

    private val _formattedAddress =
        mutableSharedFlow<FormattedAddressResponseModel>()
    val formattedAddress: SharedFlow<FormattedAddressResponseModel>
        get() = _formattedAddress

    private val _geoAddress =
        mutableSharedFlow<LatLngResponseModel>()
    val geoAddress: SharedFlow<LatLngResponseModel>
        get() = _geoAddress

    private val _currentLocation = mutableSharedFlow<Location>()
    val currentLocation: SharedFlow<Location>
        get() = _currentLocation

    init {
        launch {
            locationEmitter.getCurrentLocation().collect {
                _currentLocation.emit(it)
            }
        }
    }

    fun getFormattedAddress(latLng: LatLng) {
        launch {
            _formattedAddress.emit(geolocationRepository.getFormattedAddress(
                latLng.latitude,
                latLng.longitude
            ))
        }
    }

    fun getLatLongOfAddress(address: String) {
        launch {
           launch {
               _geoAddress.emit(geolocationRepository.getLatLongOfAddress(address))
           }
        }
    }

}