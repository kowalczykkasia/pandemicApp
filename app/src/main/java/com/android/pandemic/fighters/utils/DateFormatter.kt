package com.android.pandemic.fighters.utils

import android.text.format.DateFormat
import java.util.Calendar
import java.util.Locale

object DateFormatter {

    fun getDataFromTimestamp(timestamp: Long): CharSequence {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = timestamp * 1000
        return DateFormat.format("dd.MM.yyyy HH:ss", calendar).toString()
    }
}