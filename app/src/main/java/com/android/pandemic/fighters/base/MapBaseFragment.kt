package com.android.pandemic.fighters.base

import android.location.Location
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.android.pandemic.fighters.R
import com.android.pandemic.fighters.utils.DEFAULT_ZOOM
import com.android.pandemic.fighters.utils.extensions.generateBitmapDescriptorFromRes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

abstract class MapBaseFragment<T: ViewBinding> : BaseFragment<T>(), OnMapReadyCallback {

    var map: GoogleMap? = null
    private var currentLocationMarker: Marker? = null

    override fun onMapReady(map: GoogleMap) {
        this.map = map
    }

    fun setCurrentLocation(location: Location?, moveCamera: Boolean = false, showCurrentLocationPin: Boolean = true) {
        map?.apply {
            location?.let { location ->
                currentLocationMarker?.remove() ?: kotlin.run {
                    map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), DEFAULT_ZOOM))
                }
                if(showCurrentLocationPin) currentLocationMarker = addMarker(createMarker(LatLng(location.latitude, location.longitude), R.drawable.ic_location_pin))
                if(moveCamera)  map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), DEFAULT_ZOOM))
            }
        }
    }

    fun addMarker(latLng: LatLng) {
        map?.addMarker(createMarker(latLng))
    }

    fun createMarker(latLng: LatLng, icon: Int = R.drawable.ic_pin) =
        MarkerOptions().position(latLng).apply {
            icon(requireContext().generateBitmapDescriptorFromRes(icon))
        }

    abstract override fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

}