package com.revengeos.revengeui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager

class DisableScrollingLinearLayoutManager : LinearLayoutManager {

    var canScroll = true

    constructor(context: Context) : super(context)
    constructor(context: Context, orientation: Int, reverseLayout: Boolean)
            : super(context, orientation, reverseLayout)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    override fun canScrollVertically(): Boolean {
        return canScroll && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return canScroll && super.canScrollHorizontally()
    }
}