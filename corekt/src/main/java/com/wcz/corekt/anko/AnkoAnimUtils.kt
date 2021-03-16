package com.lodz.android.core.anko

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import androidx.annotation.AnimRes

/**
 * 开始动画
 * @param context 上下文
 * @param view 控件
 * @param animResId 动画资源id
 * @param visibility 动画结束后的显隐状态
 */
fun View.startAnim(context: Context, @AnimRes animResId: Int, visibility: Int) {
    clearAnimation()
    val animation = AnimationUtils.loadAnimation(context, animResId)
    startAnimation(animation)
    setVisibility(visibility)
//    this.visibility = visibility
}

/** 旋转自身，起始角度[fromDegrees]，结束角度[toDegrees]，时间[duration]，动画转化结束后被应用[fillAfter] */
fun View.startRotateSelf(fromDegrees: Int, toDegrees: Int, duration: Long, fillAfter: Boolean) {
    startRotateSelf(fromDegrees.toFloat(), toDegrees.toFloat(), duration, fillAfter)
}

/**
 * 旋转自身
 * @param fromDegrees 起始角度
 * @param toDegrees 结束角度
 * @param duration 时间
 * @param fillAfter 动画转化结束后被应用
 */
fun View.startRotateSelf(fromDegrees: Float, toDegrees: Float, duration: Long, fillAfter: Boolean) {
    clearAnimation()
    val animation: Animation = RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    animation.duration = duration
    animation.fillAfter = fillAfter //设置为true，动画转化结束后被应用
    startAnimation(animation) //开始动画
}


/** 放大自身，
 * X轴起始大小[fromX]（0.0~n）1.0表示控件原始大小。
 * X轴结束大小[toX]（0.0~n）1.0表示控件原始大小。
 * Y轴起始大小[fromY]（0.0~n）1.0表示控件原始大小。
 * Y轴结束大小[toY]（0.0~n）1.0表示控件原始大小。
 * 时间[duration]，动画转化结束后被应用[fillAfter]
 */
fun View.startScaleSelf(fromX: Int, toX: Int, fromY: Int, toY: Int, duration: Long, fillAfter: Boolean) {
    startScaleSelf(fromX.toFloat(), toX.toFloat(), fromY.toFloat(), toY.toFloat(), duration, fillAfter)
}

/**
 * 放大自身
 * @param fromX X轴起始大小（0.0~n），1.0表示控件原始大小
 * @param toX X轴结束大小（0.0~n），1.0表示控件原始大小
 * @param fromY Y轴起始大小（0.0~n），1.0表示控件原始大小
 * @param toY Y轴结束大小（0.0~n），1.0表示控件原始大小
 * @param duration 时间
 * @param fillAfter 动画转化结束后被应用
 */
fun View.startScaleSelf(fromX: Float, toX: Float, fromY: Float, toY: Float, duration: Long, fillAfter: Boolean) {
    clearAnimation()
    val animation: Animation = ScaleAnimation(fromX, toX, fromY, toY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    animation.duration = duration
    animation.fillAfter = fillAfter
    startAnimation(animation) //开始动画
}

