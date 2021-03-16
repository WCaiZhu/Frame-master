package com.wcz.corekt.anko

import android.text.Editable

/**
 * String扩展类
 * @author Wuczh
 * @date 2021/3/15
 */
/** 把String转为Editable */
fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

/** 在String尾部进行文字拼接 */
fun <T> String.append(t: T?): String =
    StringBuilder(this).append((t != null).then { t } ?: "").toString()