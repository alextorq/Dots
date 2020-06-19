package com.example.dots.utils

import android.R
import android.content.Context
import android.util.TypedValue
import com.example.dots.interfaces.ScreenSizes

fun sizeParams(context: Context): ScreenSizes {

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getActionBarHeight(): Int {
        var actionBarHeight: Int = 0;
        val tv = TypedValue()
        if (context.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
            actionBarHeight  =
                TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
        }
        return actionBarHeight;
    }

    val result = object: ScreenSizes {
        override val statusBarHeight: Int = getStatusBarHeight()
        override val actionBarHeight: Int = getActionBarHeight()
        override var width: Int = context.resources.getDisplayMetrics().widthPixels
        override var height: Int = context.resources.getDisplayMetrics().heightPixels
        override var yOffset = statusBarHeight + actionBarHeight
    }

    return result
}