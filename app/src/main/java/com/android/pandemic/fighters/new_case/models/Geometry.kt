package com.android.pandemic.fighters.new_case.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Geometry(
    val location: Location
) : Parcelable