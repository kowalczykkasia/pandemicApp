package com.android.pandemic.fighters.home.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reported_cases")
data class Document(
    @Embedded
    val fields: Fields,
    @PrimaryKey val name: String,
)