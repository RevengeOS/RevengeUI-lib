package com.revengeos.revengeui.effect

import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

class SpringEffectProvider {

    companion object Params {
        const val INITIAL_SCALE = 1f
        const val STIFFNESS = 300f
        const val DAMPING_RATIO = 0.25f
        const val LONG_PRESS_TIMEOUT = 175L
    }

    private lateinit var scaleXAnimation : SpringAnimation
    private lateinit var scaleYAnimation : SpringAnimation
    private lateinit var animatorX : ObjectAnimator
    private lateinit var animatorY : ObjectAnimator

    private var mIsPressed = false
    private var mLongPressed = false
    private var mIgnoreTouch = false

    private var mSpringEffectScale = 1f

    private val longPressListener = Runnable {
        if (mIsPressed) {
            mLongPressed = true
        }
        if (!mIsPressed && !mLongPressed) {
            clearAnimations()
            scaleXAnimation.start()
            scaleYAnimation.start()
        }
    }

    private lateinit var listenView: View
    private lateinit var animateView: View

    constructor(view: View) {
        listenView = view
        animateView = view
    }

    public fun setViewToAnimate(view : View) {
        animateView = view
        createAnimations()
    }

    public fun createAnimations() {
        if (this::animatorX.isInitialized) {
            clearAnimations()
        }
        scaleXAnimation = createSpringAnimation(
            animateView,
            SpringAnimation.SCALE_X,
            INITIAL_SCALE,
            STIFFNESS,
            DAMPING_RATIO
        )

        scaleYAnimation = createSpringAnimation(
            animateView,
            SpringAnimation.SCALE_Y,
            INITIAL_SCALE,
            STIFFNESS,
            DAMPING_RATIO
        )

        updateObjectAnimators()
    }

    public fun updateSpringScale(scale : Float) {
        if (scale != mSpringEffectScale) {
            mSpringEffectScale = scale
            createAnimations()
        }
    }

    private fun updateObjectAnimators() {
        animatorX = ObjectAnimator.ofFloat(animateView, "scaleX", 1.0f, mSpringEffectScale)
        animatorY = ObjectAnimator.ofFloat(animateView, "scaleY", 1.0f, mSpringEffectScale)
    }

    public fun addSpringEffect() {
        if (!this::animatorX.isInitialized) {
            createAnimations()
        }
        listenView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                if (!mIgnoreTouch && event != null && view != null) {
                    if (event.action == MotionEvent.ACTION_DOWN && !mIsPressed) {
                        view.handler.removeCallbacks(longPressListener)
                        view.handler.postDelayed(longPressListener, LONG_PRESS_TIMEOUT)
                        clearAnimations()
                        animatorX.start()
                        animatorY.start()
                        mIsPressed = true
                    } else if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) && mIsPressed) {
                        if (mLongPressed) {
                            clearAnimations()
                            scaleXAnimation.start()
                            scaleYAnimation.start()
                            mLongPressed = false
                        }
                        mIsPressed = false
                    }
                }
                return view?.onTouchEvent(event) ?: true
            }
        })
    }

    public fun setIgnoreTouch(value : Boolean) {
        mIgnoreTouch = value
    }

    public fun removeSpringEffect() {
        listenView.setOnTouchListener(null)
        clearAnimations()
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