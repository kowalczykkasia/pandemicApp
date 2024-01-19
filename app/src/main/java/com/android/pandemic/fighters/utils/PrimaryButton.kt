package com.android.pandemic.fighters.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Keep
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.android.pandemic.fighters.R
import com.android.pandemic.fighters.utils.extensions.gone
import com.android.pandemic.fighters.utils.extensions.invisible
import com.android.pandemic.fighters.utils.extensions.setImageResource
import com.android.pandemic.fighters.utils.extensions.visible

class PrimaryButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var loaderView: LottieAnimationView
    private var buttonPrimary: ConstraintLayout
    private var card: CardView
    private var tvTitle: TextView
    private var ivIcon: ImageView
    private var state: ButtonState = ButtonState.DISABLED
    private var bgColor: Int = R.color.primary_color

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
                bgColor = getResourceId(R.styleable.CustomButton_bgColor, R.color.primary_color)
                buttonPrimary.background = ContextCompat.getDrawable(context, bgColor)
                setImageResource(ivIcon)
                setState(getBoolean(R.styleable.CustomButton_isActive, true))
                recycle()
            }
        }
    }

    fun setState(isActive: Boolean){
        if (isActive) setActive()
        else setDisabled()
    }

    fun setLoading(){
        state = ButtonState.LOADING
        loaderView.visible()
        buttonPrimary.apply{
            buttonPrimary.setBackgroundColor(ContextCompat.getColor(context, R.color.disabled_background))
            isClickable = false
        }
        tvTitle.invisible()
    }

    fun setDisabled(){
        state = ButtonState.DISABLED
        loaderView.gone()
        buttonPrimary.apply{
            buttonPrimary.setBackgroundColor(ContextCompat.getColor(context, R.color.disabled_background))
            isClickable = false
        }
        tvTitle.visible()
    }

    fun setActive(){
        state = ButtonState.ACTIVE
        loaderView.gone()
        buttonPrimary.apply{
            buttonPrimary.setBackgroundColor(ContextCompat.getColor(context, bgColor))
            isClickable = true
        }
        tvTitle.visible()
    }

    override fun setOnClickListener(onClick: OnClickListener?){
        buttonPrimary.setOnClickListener(onClick)
    }
}

@Keep
enum class ButtonState {
    ACTIVE,
    DISABLED,
    LOADING
}