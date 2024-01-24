package com.android.pandemic.fighters.new_case.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    @SerializedName("formatted_address") val formattedAddress: String,
    val geometry: Geometry
) : Parcelable