package com.wcz.corekt.utils

import android.os.Build
import androidx.annotation.StringDef
import java.io.File
import java.util.*

/**
 *设备帮助类
 * @author Wuczh
 * @date 2021/3/12
 */
object DeviceUtils {
    /** 品牌 */
    const val BRAND = "BRAND"
    /** 型号 */
    const val MODEL = "MODEL"
    /** 模板 */
    const val BOARD = "BOARD"
    /** CPU1 */
    const val CPU_ABI = "CPU_ABI"
    /** CPU2 */
    const val CPU_ABI2 = "CPU_ABI2"
    /** 制造商 */
    const val MANUFACTURER = "MANUFACTURER"
    /** 产品 */
    const val PRODUCT = "PRODUCT"
    /** 设备 */
    const val DEVICE = "DEVICE"

    @StringDef(
        BRAND,
        MODEL,
        BOARD,
        CPU_ABI,
        CPU_ABI2,
        MANUFACTURER,
        PRODUCT,
        DEVICE
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class DeviceKey

    /** 获取手机的UA信息 */
    @JvmStatic
    fun getUserAgent():String{
        try {
            return System.getProperty("http.agent")?:""
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /** 获取语言 */
    @JvmStatic
    fun getLanguage(): String = Locale.getDefault().language ?: ""

    /** 获取国家 */
    @JvmStatic
    fun getCountry(): String = Locale.getDefault().country ?: ""

    /** 获取设备信息 */
    @JvmStatic
    fun getDeviceInfo(): Map<String, String>{
        val infos=HashMap<String, String>()
        try {
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            field.isAccessible=true
            infos[field.name] = field.get(null)?.toString() ?: ""
        }
        } catch (e: Exception ) {
            e.printStackTrace()
        }
        return infos
    }


    /** 校验su文件路径数组[paths]（不传或传null使用默认校验路径），不存在su文件返回null */
    @JvmStatic
    @JvmOverloads
    fun checkRootFile(paths: Array<String>? = null): File? {
        var checkPaths = arrayOf(
            "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su",
            "/data/local/bin/su", "/system/sd/xbin/su", "/system/bin/failsafe/su", "/data/local/su"
        )
        if (paths != null && paths.isNotEmpty()) {//如果
            checkPaths = paths
        }
        for (path in checkPaths) {
            val file = File(path)
            if (file.exists()) {//存在返回文件
                return file
            }
        }
        return null
    }

    /** 手机是否root，通过校验默认位置的su文件，存在一定误差 */
    @JvmStatic
    fun isRoot(): Boolean = checkRootFile() != null
}