package com.android.pandemic.fighters.utils.extensions

import android.view.View

fun View.gone() {
    this.visibility = View.GONE
}

fun View.goneIf(shouldGone: Boolean) {
    this.visibility = if (shouldGone) View.GONE else View.VISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}