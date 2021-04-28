package com.revengeos.revengeui.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;

import java.util.List;

/**
 * A AppBarLayout.ScrollingViewBehavior that adapts the scrolling view height based on its content.
 * Specifically, the final height is a compromise among:
 * - visible height (e.g. height not occupied by the appbar itself)
 * - wrap content height (e.g. total height of the scrolling content)
 * - available height (e.g. total CoordinatorLayout height)
 *
 * For this to work, you need:
 * - set this behavior to the scrolling view
 * - have match_parent as height
 * - have at least one of the AppBarLayout childs use the flags:
 *
 *     app:layout_scrollFlags="scroll|exitUntilCollapsed"
 */
public class ConstrainedScrollBehavior extends AppBarLayout.ScrollingViewBehavior {

    private final static String TAG = ConstrainedScrollBehavior.class.getSimpleName();

    private int ablOriginalMinHeight = -1;

    public ConstrainedScrollBehavior() {
    }

    public ConstrainedScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected AppBarLayout findAppBar(List<View> dependencies) {
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            View view = dependencies.get(i);
            if (view instanceof AppBarLayout) {
                return (AppBarLayout) view;
            }
        }
        return null;
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        final int childLpHeight = child.getLayoutParams().height;
        // Log.e(TAG, "onMeasureChild: getLayoutParams().height is "+childLpHeight+" (-1=MP, -2=WC)");
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT) {

            final List<View> dependencies = parent.getDependencies(child);
            final AppBarLayout header = findAppBar(dependencies);
            if (header != null) {

                // TODO: If we had some height changes inside, at the end of the function we 
                // will update the abl scroll range based on new height. However, for some reason,
                // that won't work if the abl is fully expanded (offset == 0). Seems like it is not
                // properly invalidated, even though we set the right value.
                // This is a dirty workaround.
                CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) header.getLayoutParams();
                AppBarLayout.Behavior b = (AppBarLayout.Behavior) p.getBehavior();
                if (b.getTopAndBottomOffset() == 0) {
                    b.setTopAndBottomOffset(-1);
                }

                // Check original minHeight if needed.
                if (ablOriginalMinHeight == -1) {
                    ablOriginalMinHeight = header.getMeasuredHeight() - header.getTotalScrollRange();
                }

                // FitsSystemWindows stuff.
                if (ViewCompat.getFitsSystemWindows(header) && !ViewCompat.getFitsSystemWindows(child)) {
                    ViewCompat.setFitsSystemWindows(child, true);
                    if (ViewCompat.getFitsSystemWindows(child)) {
                        // If the set succeeded, trigger a new layout and return true
                        child.requestLayout();
                        return true;
                    }
                }

                // Get available height as imposed by the parent (the "screen height").
                int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
                if (availableHeight == 0) {
                    // If the measure spec doesn't specify a size, use the current height
                    availableHeight = parent.getHeight();
                }

                // Get AppBarLayout measured height.
                int headerHeight = header.getMeasuredHeight();

                // Get visible height, the one we are aiming at.
                // The default was availableHeight - header.getMeasuredHeight() + header.getTotalScrollRange()
                final int visibleHeight = availableHeight - headerHeight;

                // Measure wrap-content height: if it's big enough, that's OK for us.
                int wcMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, wcMeasureSpec, heightUsed);
                final int wcHeight = child.getMeasuredHeight();

                // If it's not big enough, then we must measure again and assign the whole visibleHeight.
                // -> The largest of the two
                final int desiredHeight = Math.max(visibleHeight, wcHeight);
                // -> No more than the available, or the bottom part will be hidden
                //    (Coordinator does not scroll itself)
                final int finalHeight = Math.min(desiredHeight, availableHeight);
                final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(finalHeight, View.MeasureSpec.EXACTLY);

                // Measure again :-( in most cases.
                if (finalHeight != wcHeight) {
                    parent.onMeasureChild(child, parentWidthMeasureSpec,
                            widthUsed, heightMeasureSpec, heightUsed);
                }
                return true;
            }
        }
        return super.onMeasureChild(parent, child, parentWidthMeasureSpec, widthUsed,
                parentHeightMeasureSpec, heightUsed);
    }
}
