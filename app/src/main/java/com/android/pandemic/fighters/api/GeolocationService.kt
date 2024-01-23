package com.android.pandemic.fighters.api

import com.android.pandemic.fighters.BuildConfig
import com.android.pandemic.fighters.new_case.models.FormattedAddressResponseModel
import com.android.pandemic.fighters.new_case.models.LatLngResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GeolocationService {

    @GET("json")
    suspend fun getFormattedAddress(
        @Query("latlng") latLng: String,
        @Query("key") key: String = BuildConfig.MAPS_API_KEY
    ) : FormattedAddressResponseModel

    @GET("json")
    suspend fun getLatLongOfAddress(
        @Query("address") latLng: String,
        @Query("key") key: String = BuildConfig.MAPS_API_KEY
    ) : LatLngResponseModel

}