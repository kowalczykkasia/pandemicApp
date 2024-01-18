package com.android.pandemic.fighters.api

import com.android.pandemic.fighters.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface GeolocationService {

    @GET("json")
    fun getFormattedAddress(
        @Query("latlng") latLng: String,
        @Query("key") key: String = BuildConfig.MAPS_API_KEY
    )

}