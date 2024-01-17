package com.android.pandemic.fighters.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.InflateException
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Keep
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.getResourceIdOrThrow
import com.airbnb.lottie.LottieAnimationView
import com.android.pandemic.fighters.R
import java.lang.reflect.InvocationTargetException

class PrimaryButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var loaderView: LottieAnimationView
    private var buttonPrimary: ConstraintLayout
    private var card: CardView
    private var tvTitle: TextView
    private var ivIcon: ImageView
    private var state: ButtonState = ButtonState.DISABLED

    init {
        inflate(context, R.layout.primary_button, this)
        loaderView = findViewById(R.id.loaderview)
        card = findViewById(R.id.card)
        buttonPrimary = findViewById(R.id.buttonPrimary)
        tvTitle = findViewById(R.id.tvTitle)
        ivIcon = findViewById(R.id.ivIcon)
        init(attrs)
    }

    @SuppressLint("CustomViewStyleable")
    private fun init(set: AttributeSet?) {
        set?.let {
            context.obtainStyledAttributes(it, R.styleable.CustomButton).run {
                tvTitle.text = getString(R.styleable.CustomButton_text)
                val bgColor = getResourceId(R.styleable.CustomButton_bgColor, R.color.primary_color)
                buttonPrimary.background = ContextCompat.getDrawable(context, bgColor)
                setImageResource(ivIcon)
                recycle()
            }
        }
    }
}

@Keep
enum class ButtonState {
    ACTIVE,
    DISABLED,
    LOADING
}