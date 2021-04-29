package com.revengeos.revengeui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.revengeos.revengeui.recyclerview.BounceEdgeEffectFactory

class BounceRecyclerView: RecyclerView {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    init {
        edgeEffectFactory = BounceEdgeEffectFactory()
    }
}