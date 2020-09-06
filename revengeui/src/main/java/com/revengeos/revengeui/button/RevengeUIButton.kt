package com.revengeos.revengeui.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

import com.revengeos.revengeui.R

/**
 * TODO: document your custom view class.
 */
    class RevengeUIButton : AppCompatButton {

    private var mUseSpringEffect = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.buttonStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RevengeUIButton,
            0, 0)

        try {
            mUseSpringEffect = typedArray.getBoolean(R.styleable.RevengeUIButton_springEffect, false)
        } finally {
            typedArray.recycle()
        }
    }

}
