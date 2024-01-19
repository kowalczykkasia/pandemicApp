package com.android.pandemic.fighters.utils.extensions

import android.location.Location
import android.location.LocationManager
import com.google.android.gms.maps.model.LatLng

fun LatLng.distanceTo(latlng: LatLng): Int {
    val location1 = Location(LocationManager.GPS_PROVIDER);
    location1.latitude = latlng.latitude
    location1.longitude = latlng.longitude

    val location2 = Location(LocationManager.GPS_PROVIDER);
    location2.latitude = this.latitude
    location2.longitude = this.longitude

    return location1.distanceTo(location2).toInt()
}