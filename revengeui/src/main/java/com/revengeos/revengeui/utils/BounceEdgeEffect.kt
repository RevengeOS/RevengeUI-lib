/*
 * Simple Weather
 * Copyright (c) 2021 Zhenxiang Chen & Ethan Halsall
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.revengeos.revengeui.utils

import android.content.Context
import android.graphics.Canvas
import android.view.View
import android.widget.EdgeEffect
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.absoluteValue

/** The magnitude of translation distance while the list is over-scrolled. */
private const val OVERSCROLL_TRANSLATION_MAGNITUDE = 0.25f

/** The magnitude of translation distance when the list reaches the edge on fling. */
private const val FLING_TRANSLATION_MAGNITUDE = 0.15f

/**
 * Replace edge effect by a bounce
 */
class BounceEdgeEffect(context: Context, private val view: View, private val direction: Int) : EdgeEffect(context) {

    val isHorizontal = direction == RecyclerView.EdgeEffectFactory.DIRECTION_RIGHT || direction == RecyclerView.EdgeEffectFactory.DIRECTION_LEFT

    // A reference to the [SpringAnimation] for this RecyclerView used to bring the item back after the over-scroll effect.
    var translationAnim: SpringAnimation? = null

    override fun onPull(deltaDistance: Float) {
        super.onPull(deltaDistance)
        handlePull(deltaDistance)
    }

    override fun onPull(deltaDistance: Float, displacement: Float) {
        super.onPull(deltaDistance, displacement)
        handlePull(deltaDistance)
    }

    private fun handlePull(deltaDistance: Float) {
        // This is called on every touch event while the list is scrolled with a finger.

        // Translate the recyclerView with the distance
        val sign = if (direction == RecyclerView.EdgeEffectFactory.DIRECTION_BOTTOM || direction == RecyclerView.EdgeEffectFactory.DIRECTION_RIGHT) -1 else 1
        val translationDelta = sign * view.width * deltaDistance.absoluteValue * OVERSCROLL_TRANSLATION_MAGNITUDE
        if (isHorizontal) {
            view.translationX += translationDelta
        } else {
            view.translationY += translationDelta
        }

        translationAnim?.cancel()
    }

    override fun onRelease() {
        super.onRelease()
        // The finger is lifted. Start the animation to bring translation back to the resting state.
        if ((view.translationY != 0f && !isHorizontal) || (view.translationX != 0f && isHorizontal)) {
            translationAnim = createAnim()?.also { it.start() }
        }
    }

    override fun onAbsorb(velocity: Int) {
        super.onAbsorb(velocity)

        // The list has reached the edge on fling.
        val sign = if (direction == RecyclerView.EdgeEffectFactory.DIRECTION_BOTTOM || direction == RecyclerView.EdgeEffectFactory.DIRECTION_RIGHT) -1 else 1
        val translationVelocity = sign * velocity * FLING_TRANSLATION_MAGNITUDE
        translationAnim?.cancel()
        translationAnim = createAnim().setStartVelocity(translationVelocity)?.also { it.start() }
    }

    override fun draw(canvas: Canvas?): Boolean {
        // don't paint the usual edge effect
        return false
    }

    override fun isFinished(): Boolean {
        // Without this, will skip future calls to onAbsorb()
        return translationAnim?.isRunning?.not() ?: true
    }

    private fun createAnim() = SpringAnimation(view, if (isHorizontal) SpringAnimation.TRANSLATION_X else SpringAnimation.TRANSLATION_Y)
            .setSpring(SpringForce()
                    .setFinalPosition(0f)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
            )
}