package com.revengeos.revengeui.effect

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View

class LayoutAlphaTransitionHelper {

    companion object {
        const val animationLength : Long = 475
    }

    private var toggleState = false
    private val frontView : View
    private val backView : View

    constructor(frontView : View, backView : View, defaultState : Boolean) {
        this.frontView = frontView
        this.backView = backView
        toggleState = defaultState
        setState(toggleState, false, true)
    }

    /*  false to display front view
        and true to display back view
     */
    fun setState(state : Boolean, animate : Boolean, forced : Boolean) {
        if (state != toggleState || forced) {
            toggleState = state
            if (frontView == null || backView == null) {
                return
            }
            var appearingView = if (toggleState) backView else frontView
            var disappearingView = if (toggleState) frontView else backView
            if (animate) {
                var appearAnimator = ObjectAnimator.ofFloat(appearingView, "alpha", 0f, 0f, 1f)
                appearAnimator.duration = animationLength
                appearAnimator.addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        appearingView.visibility = View.VISIBLE
                    }
                })

                var disappearAnimator = ObjectAnimator.ofFloat(disappearingView, "alpha", 1f, 0f, 0f)
                disappearAnimator.duration = animationLength
                disappearAnimator.addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        disappearingView.visibility = View.GONE
                    }
                })

                appearAnimator.start()
                disappearAnimator.start()
            } else {
                appearingView.visibility = View.VISIBLE
                disappearingView.visibility = View.GONE
            }
        }
    }

    fun setState(state : Boolean, animate: Boolean) {
        setState(state, animate, false)
    }
    fun setState(state : Boolean) {
        setState(state, true)
    }
}