package com.waynils.exoapplication.helper.extension

import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun View.getColor(@ColorRes color: Int): Int {
    return ContextCompat.getColor(context, color)
}