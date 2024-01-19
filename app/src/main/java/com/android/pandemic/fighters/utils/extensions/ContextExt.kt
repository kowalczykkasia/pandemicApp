package com.android.pandemic.fighters.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun Context.generateBitmapDescriptorFromRes(resId: Int): BitmapDescriptor? =
    ContextCompat.getDrawable(this, resId)?.let {
        it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        it.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }