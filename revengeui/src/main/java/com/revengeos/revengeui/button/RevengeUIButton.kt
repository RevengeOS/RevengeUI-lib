package com.revengeos.revengeui.button

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

import com.revengeos.revengeui.R

/**
 * TODO: document your custom view class.
 */
class RevengeUIButton : AppCompatButton {

    private companion object Params {
        const val INITIAL_SCALE = 1f
        const val STIFFNESS = 300f
        const val DAMPING_RATIO = 0.25f
    }

    private val scaleXAnimation = createSpringAnimation(
        this,
        SpringAnimation.SCALE_X,
        INITIAL_SCALE,
        STIFFNESS,
        DAMPING_RATIO
    )
    private val scaleYAnimation = createSpringAnimation(
        this,
        SpringAnimation.SCALE_Y,
        INITIAL_SCALE,
        STIFFNESS,
        DAMPING_RATIO
    )
    private val animatorX = ObjectAnimator.ofFloat(this, "scaleX", 1.0f, 0.9f)
    private val animatorY = ObjectAnimator.ofFloat(this, "scaleY", 1.0f, 0.9f)

    private var mUseSpringEffect = false
    private var mIsPressed = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.buttonStyle)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RevengeUIButton,
            defStyleAttr, 0
        )

        try {
            mUseSpringEffect =
                typedArray.getBoolean(R.styleable.RevengeUIButton_springEffect, false)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && mUseSpringEffect) {
            if (event.action == MotionEvent.ACTION_DOWN && !mIsPressed) {
                clearAnimations()
                animatorX.start()
                animatorY.start()
                mIsPressed = true
            } else if (event.action == MotionEvent.ACTION_UP && mIsPressed) {
                clearAnimations()
                scaleXAnimation.start()
                scaleYAnimation.start()
                mIsPressed = false
            }
        }
        return super.onTouchEvent(event)
    }

    fun setUseSpringEffect(value: Boolean) {
        clearAnimations()
        mUseSpringEffect = value
    }

    private fun clearAnimations() {
        animatorY.cancel()
        animatorX.cancel()
        scaleXAnimation.cancel()
        scaleYAnimation.cancel()
    }

    private fun createSpringAnimation(
        view: View,
        property: DynamicAnimation.ViewProperty,
        finalPosition: Float,
        stiffness: Float,
        dampingRatio: Float
    ): SpringAnimation {
        val animation = SpringAnimation(view, property)
        val spring = SpringForce(finalPosition)
        spring.stiffness = stiffness
        spring.dampingRatio = dampingRatio
        animation.spring = spring
        return animation
    }

}
