package com.wcz.corekt.anko

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 *上下文兼容扩展类
 * @author Wuczh
 * @date 2021/3/4
 */

@ColorInt
fun Context.getColorCompat(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

fun Context.getDrawableCompat(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)

@ColorInt
fun View.getColorCompat(@ColorRes id: Int): Int = ContextCompat.getColor(context, id)

fun View.getDrawableCompat(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(context, id)
