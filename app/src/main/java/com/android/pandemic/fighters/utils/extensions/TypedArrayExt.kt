package com.android.pandemic.fighters.utils.extensions

import android.content.res.TypedArray
import android.widget.ImageView
import androidx.core.content.res.getResourceIdOrThrow
import com.android.pandemic.fighters.R

fun TypedArray.setImageResource(imageView: ImageView, defaultResources: Int? = null) {
    try {
        imageView.setImageResource(getResourceIdOrThrow(R.styleable.CustomButton_btnIcon))
    } catch (e: IllegalArgumentException) {
        defaultResources?.let { imageView.setImageResource(it) } ?: kotlin.run { imageView.gone() }
    }
}