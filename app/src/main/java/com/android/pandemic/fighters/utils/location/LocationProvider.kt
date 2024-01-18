package com.android.pandemic.fighters.utils.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("ServiceCast")
class LocationProvider(private val context: Context, private val locationEmitter: LocationEmitter) {

    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                GlobalScope.launch(Dispatchers.IO) { locationEmitter.setCurrentLocation(it) }
            }
        } else {
            // Handle the case where the app doesn't have location permission
            // You might want to request permission or inform the user
        }
    }

    fun stopLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.flushLocations()
            fusedLocationClient.removeLocationUpdates {}
        }
    }
}