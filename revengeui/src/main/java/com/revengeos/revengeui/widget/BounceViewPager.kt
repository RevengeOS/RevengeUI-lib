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

package com.revengeos.revengeui.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.revengeos.revengeui.utils.BounceEdgeEffect
import com.revengeos.revengeui.utils.ReflectionUtils

open class BounceViewPager: ViewPager {

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    init {
        ReflectionUtils.getField(ViewPager::class.java.name, "mLeftEdge").set(this, BounceEdgeEffect(context, this, RecyclerView.EdgeEffectFactory.DIRECTION_LEFT))
        ReflectionUtils.getField(ViewPager::class.java.name, "mRightEdge").set(this, BounceEdgeEffect(context, this, RecyclerView.EdgeEffectFactory.DIRECTION_RIGHT))
    }
}