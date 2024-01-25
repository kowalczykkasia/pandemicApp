package com.android.pandemic.fighters.home.models

import androidx.room.Embedded

data class Fields(
    @Embedded(prefix = "description")
    val description: StringValue,
    @Embedded(prefix = "address")
    val address: StringValue,
    @Embedded(prefix = "latitude")
    val latitude: DoubleValue,
    @Embedded(prefix = "longitude")
    val longitude: DoubleValue,
    @Embedded(prefix = "timestamp")
    val timestamp: StringValue
)