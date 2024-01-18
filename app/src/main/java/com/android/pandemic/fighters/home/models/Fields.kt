package com.android.pandemic.fighters.home.models

import com.google.gson.annotations.SerializedName

data class Fields(
    val description: StringValue,
    val address: StringValue,
    val latitude: DoubleValue,
    val longitude: DoubleValue,
    val timestamp: StringValue,
    @SerializedName("user_email") val userEmail: StringValue
)