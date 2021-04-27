package com.revengeos.revengeui.recyclerview

import android.widget.EdgeEffect
import androidx.recyclerview.widget.RecyclerView
import com.revengeos.revengeui.utils.BounceEdgeEffect

/**
 * Replace edge effect by a bounce
 */
class BounceEdgeEffectFactory : RecyclerView.EdgeEffectFactory() {

    override fun createEdgeEffect(recyclerView: RecyclerView, direction: Int): EdgeEffect {

        return BounceEdgeEffect(recyclerView.context, recyclerView, direction)
    }
}