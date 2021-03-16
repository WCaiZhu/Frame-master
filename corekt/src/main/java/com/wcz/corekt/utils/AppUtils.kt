package com.wcz.corekt.utils

import android.os.Looper
import java.util.*

/**
 * APP帮助类
 * @author Wuczh
 * @date 2021/3/15
 */
object AppUtils {

    /** 当前是否在主线程（UI线程） */
    @JvmStatic
    fun isMainThread(): Boolean = Looper.myLooper() == Looper.getMainLooper()

    /** 获取36位随机UUID */
    @JvmStatic
    fun getUUID36(): String = UUID.randomUUID().toString()

    /** 获取32位随机UUID */
    @JvmStatic
    fun getUUID32(): String = UUID.randomUUID().toString().replace("-", "")
}