package com.revengeos.revengeui.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageButton
import com.revengeos.revengeui.R
import com.revengeos.revengeui.effect.SpringEffectProvider

class RevengeUIImageButton : AppCompatImageButton {

    private var mUseSpringEffect = false
    private var mSpringEffectScale = 1f
    private var springEffectProvider : SpringEffectProvider

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.imageButtonStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        springEffectProvider = SpringEffectProvider(this)
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RevengeUIButton,
            defStyleAttr, 0
        )

        try {
            mSpringEffectScale = typedArray.getFloat(R.styleable.RevengeUIButton_springEffectScale, 0.9f)
            springEffectProvider.updateSpringScale(mSpringEffectScale)
            mUseSpringEffect =
                typedArray.getBoolean(R.styleable.RevengeUIButton_springEffect, false)
            if (mUseSpringEffect) {
                springEffectProvider.addSpringEffect()
            }
        } finally {
            typedArray.recycle()
        }
    }

    fun setUseSpringEffect(value: Boolean) {
        if (mUseSpringEffect != value) {
            if (value) {
                springEffectProvider.addSpringEffect()
            } else {
                springEffectProvider.removeSpringEffect()
            }
            mUseSpringEffect = value
        }
    }

    fun setSpringEffectScale(scale: Float) {
        mSpringEffectScale = scale
        springEffectProvider.updateSpringScale(mSpringEffectScale)
    }
}