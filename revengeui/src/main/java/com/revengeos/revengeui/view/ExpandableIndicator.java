/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.revengeos.revengeui.view;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.util.AttributeSet;

import com.revengeos.revengeui.R;


public class ExpandableIndicator extends androidx.appcompat.widget.AppCompatImageView {

    private boolean mExpanded;
    private boolean mIsDefaultDirection = true;

    public ExpandableIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateIndicatorDrawable();
        setContentDescription(getContentDescription(mExpanded));
    }

    public void setExpanded(boolean expanded) {
        if (expanded == mExpanded) return;
        mExpanded = expanded;
        final int res = getDrawableResourceId(!mExpanded);
        // workaround to reset drawable
        final AnimatedVectorDrawable avd = (AnimatedVectorDrawable) getContext()
                .getDrawable(res).getConstantState().newDrawable();
        setImageDrawable(avd);
        avd.start();
        setContentDescription(getContentDescription(expanded));
    }

    public void toggleExpansion() {
        setExpanded(!mExpanded);
    }

    /** Whether the icons are using the default direction or the opposite */
    public void setDefaultDirection(boolean isDefaultDirection) {
        mIsDefaultDirection = isDefaultDirection;
        updateIndicatorDrawable();
    }

    private int getDrawableResourceId(boolean expanded) {
        if (mIsDefaultDirection) {
            return expanded ? R.drawable.ic_collapse_animation
                    : R.drawable.ic_expand_animation;
        } else {
            return expanded ? R.drawable.ic_expand_animation
                    : R.drawable.ic_collapse_animation;
        }
    }

    private String getContentDescription(boolean expanded) {
        return expanded ? getContext().getString(R.string.accessibility_collapse)
                : getContext().getString(R.string.accessibility_expand);
    }

    private void updateIndicatorDrawable() {
        final int res = getDrawableResourceId(mExpanded);
        setImageResource(res);
    }
}
