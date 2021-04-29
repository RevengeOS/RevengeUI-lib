package com.revengeos.revengeui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.revengeos.revengeui.utils.BounceEdgeEffect
import com.revengeos.revengeui.utils.ReflectionUtils

class BounceNestedScrollView: NestedScrollView {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    init {
        ReflectionUtils.getField(NestedScrollView::class.java.name, "mEdgeGlowTop").set(this, BounceEdgeEffect(context, this, RecyclerView.EdgeEffectFactory.DIRECTION_TOP))
        ReflectionUtils.getField(NestedScrollView::class.java.name, "mEdgeGlowBottom").set(this, BounceEdgeEffect(context, this, RecyclerView.EdgeEffectFactory.DIRECTION_BOTTOM))
    }
}

