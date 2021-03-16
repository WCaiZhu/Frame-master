package com.wcz.corekt.anko

/**
 * Boolean型扩展类
 * @author Wuczh
 * @date 2021/3/4
 */
/** 替代java三目运算符（condition ? a : b）为 condition.then { a } ?: b */
infix fun <T> Boolean.then(param: () -> T): T? = if (this) param() else null

