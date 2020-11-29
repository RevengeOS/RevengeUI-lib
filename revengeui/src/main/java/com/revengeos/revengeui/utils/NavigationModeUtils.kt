package com.revengeos.revengeui.utils

import android.content.Context

class NavigationModeUtils {

    companion object {
        fun isFullGestures(context: Context): Boolean {
            val resources = context.resources
            val resourceId =
                resources.getIdentifier("config_navBarInteractionMode", "integer", "android")
            return if (resourceId > 0) {
                resources.getInteger(resourceId) == 2
            } else false
        }
    }
}