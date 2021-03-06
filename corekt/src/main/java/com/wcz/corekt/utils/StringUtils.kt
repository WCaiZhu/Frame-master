package com.wcz.corekt.utils

import java.net.URLDecoder
import java.net.URLEncoder

/**
 * 字符串帮助类
 * @author Wuczh
 * @date 2021/3/15
 */
object StringUtils {
    /** 用UTF-8解码文字[str] */
    @JvmStatic
    fun decodeUtf8(str: String): String {
        try {
            if (str.isNotEmpty()) {
                return URLDecoder.decode(str, "UTF-8")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /** 用UTF-8编码文字[str] */
    @JvmStatic
    fun encodeUtf8(str: String): String {
        try {
            if (str.isNotEmpty()) {
                return URLEncoder.encode(str, "UTF-8")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /** 根据分隔符[separator]将字符串[str]转为列表 */
    @JvmStatic
    fun getListBySeparator(str: String, separator: String): List<String> {
        var source = str
        val list = ArrayList<String>()
        while (source.contains(separator)) {
            val value = source.substring(0, source.indexOf(separator))
            if (value.isNotEmpty()) {
                list.add(value)
            }
            source = source.substring(source.indexOf(separator) + 1, source.length)
        }
        if (source.isNotEmpty()) {
            list.add(source)
        }
        return list
    }

    /** 根据分隔符[separator]将字符串[str]转为数组 */
    @JvmStatic
    fun getArrayBySeparator(str: String, separator: String): Array<String> = getListBySeparator(str, separator).toTypedArray()

    /** 根据分隔符[separator]组装列表[list]元素 */
    @JvmStatic
    fun getStringBySeparator(list: List<String>, separator: String): String {
        var result = ""
        if (list.isEmpty()) {
            return result
        }
        for (i in list.indices) {
            result = result + list[i] + if (i == (list.size - 1)) "" else separator
        }
        return result
    }

    /** 根据分隔符[separator]组装数组[array]元素 */
    @JvmStatic
    fun getStringBySeparator(array: Array<String>, separator: String): String = getStringBySeparator(array.toList(), separator)

}