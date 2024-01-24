package com.android.pandemic.fighters.new_case

import android.location.Location
import androidx.lifecycle.viewModelScope
import com.android.pandemic.fighters.base.BaseViewModel
import com.android.pandemic.fighters.base.ResponseState
import com.android.pandemic.fighters.base.mutableSharedFlow
import com.android.pandemic.fighters.new_case.models.FormattedAddressResponseModel
import com.android.pandemic.fighters.new_case.models.LatLngResponseModel
import com.android.pandemic.fighters.repositories.GeolocationRepository
import com.android.pandemic.fighters.utils.location.LocationEmitter
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapAddressSelectionViewModel @Inject constructor(
    private val geolocationRepository: GeolocationRepository,
    private val locationEmitter: LocationEmitter
) : BaseViewModel() {

    private val _formattedAddress =
        mutableSharedFlow<ResponseState<FormattedAddressResponseModel>>()
    val formattedAddress: SharedFlow<ResponseState<FormattedAddressResponseModel>>
        get() = _formattedAddress

    private val _geoAddress =
        mutableSharedFlow<ResponseState<LatLngResponseModel>>()
    val geoAddress: SharedFlow<ResponseState<LatLngResponseModel>>
        get() = _geoAddress

    private val _currentLocation = mutableSharedFlow<Location>()
    val currentLocation: SharedFlow<Location>
        get() = _currentLocation

    init {
        viewModelScope.launch(Dispatchers.IO) {
            locationEmitter.getCurrentLocation().collect {
                _currentLocation.emit(it)
            }
        }
    }

    fun getFormattedAddress(latLng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            _formattedAddress.emit(
                geolocationRepository.getFormattedAddress(
                    latLng.latitude,
                    latLng.longitude
                )
            )
        }
    }

    fun getLatLongOfAddress(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _geoAddress.emit(geolocationRepository.getLatLongOfAddress(address))
        }
    }

}