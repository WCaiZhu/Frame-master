package com.lodz.android.core.anko

import android.Manifest
import android.app.ActivityManager
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Looper
import android.provider.Settings
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * 获取应用程序名称
 * @param context 上下文
 */
fun Context.getAppName(): String {
    try {
        return resources.getString(packageManager.getPackageInfo(packageName, 0).applicationInfo.labelRes)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""

}

/**
 * 获取客户端版本名称
 */
fun Context.getVersionName(): String {
    try {
        return packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 获取客户端版本号
 */
fun Context.getVersionCode(): Long {
    try {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageManager.getPackageInfo(packageName, 0).longVersionCode
        } else {
            packageManager.getPackageInfo(packageName, 0).versionCode.toLong()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return -1
}


/**
 * 当前是否在主线程（UI线程）
 */
fun Context.isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}

/**
 *判断传入的context是不是在主进程
 * @param context 上下文
 */
fun Context.isMainProcess(): Boolean {
    val processName = getProcessName()
    if (processName.isEmpty()) {
        return false
    }
    return !processName.contains(":")
}


/**
 * 获取进程名称
 */
fun Context.getProcessName(): String {
    val pid = android.os.Process.myPid()
    val am: ActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningApps: MutableList<ActivityManager.RunningAppProcessInfo> = am.runningAppProcesses
            ?: return ""
    for (procInfo in runningApps) {
        if (procInfo.pid == pid) {
            return procInfo.processName
        }
    }
    return ""
}

/** 判断包名为[packageName]的app是否安装 */
fun Context.isPkgInstalled(packageName: String): Boolean {
    if (packageName.isEmpty()) {
        return false
    }
    try {
        val packageInfo: PackageInfo? = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES)
        return packageInfo != null
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

/** 获取已安装的PackageInfo列表 */
fun Context.getInstalledPackages(): List<PackageInfo> =
        packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES)
                ?: Collections.emptyList()

/** 获取包名为[packageName]的PackageInfo */
fun Context.getPackageInfo(packageName: String): PackageInfo? {
    if (packageName.isEmpty()) {
        return null
    }
    try {
        return packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/** 权限[permission]是否被授予 */
fun Context.isPermissionGranted(permission: String): Boolean {
    try {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

/** 获取MetaData中标签为[key]的值 */
fun Context.getMetaData(key: String): Any? {
    try {
        val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        return appInfo.metaData.get(key)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/** GPS是否打开 */
fun Context.isGpsOpen(): Boolean {
    val str = Settings.Secure.getString(contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)
    return str.isNotEmpty() && (str.contains("gps") || str.contains("GPS"))
}

/** wifi是否可用 */
fun Context.isWifiEnabled(): Boolean = (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).isWifiEnabled

/**
 * 设置wifi是否可用
 * @param enabled 是否可用
 */
@RequiresPermission(Manifest.permission.CHANGE_WIFI_STATE)
fun Context.setWifiEnabled(enabled: Boolean) {
    val manager: WifiManager = getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
    if (manager.isWifiEnabled) {
        manager.setWifiEnabled(enabled)
    }
}

/**
 * 跳转到WIFI设置页
 */
fun Context.jumpWifiSetting() {
    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
}

/**
 * 跳转到数据流量设置页
 */
fun Context.jumpDataRoamingSetting() {
    startActivity(Intent(Settings.ACTION_DATA_ROAMING_SETTINGS))
}

/**
 * 跳转到密码设置页
 */
fun Context.jumpSetPswdSetting() {
    startActivity(Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD))
}

/** 手机是否有NFC */
@RequiresPermission(Manifest.permission.NFC)
fun Context.hasNfc(): Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)

/** NFC是否打开 */
@RequiresPermission(Manifest.permission.NFC)
fun Context.isNfcOpen(): Boolean {
    val adapter = NfcAdapter.getDefaultAdapter(this) ?: return false
    return adapter.isEnabled
}


/** 获堆栈的Activity信息 */
fun Context.getActivityRunningTaskInfo(): ActivityManager.RunningTaskInfo? {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
    val list = manager?.getRunningTasks(1)
    return list?.get(0)
}

/** 堆栈顶部和底部Activity是否一致 */
fun Context.isTopAndBottomActivityTheSame(): Boolean {
    val info = getActivityRunningTaskInfo()
    val topActivityName = info?.topActivity?.className
    val baseActivityName = info?.baseActivity?.className
    return topActivityName?.equals(baseActivityName) ?: false
}

/** 重启APP */
fun Context.restartApp() {
    val intent = packageManager.getLaunchIntentForPackage(packageName) ?: return
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    startActivity(intent)
}

/**
 * 获取Assets下的文件内容
 * fileName 文件名称
 */
fun Context.getAssetsFileContent(fileName: String): String {
    InputStreamReader(assets.open(fileName), StandardCharsets.UTF_8).use { isr: InputStreamReader ->
        BufferedReader(isr).use { br: BufferedReader ->
            var line: String?
            val builder = StringBuilder()
            while (true) {
                line = br.readLine() ?: break
                builder.append(line)
            }
            return builder.toString()
        }
    }
}

/** 获取res/raw文件夹下的文件内容
 * rawId    文件id   R.raw.xxx
 */
fun Context.getRawFileContent(rawId: Int): String {
    InputStreamReader(resources.openRawResource(rawId), StandardCharsets.UTF_8).use { isr: InputStreamReader ->
        BufferedReader(isr).use { br: BufferedReader ->
            var line: String?
            val builder = StringBuilder()
            while (true) {
                line = br.readLine() ?: break
                builder.append(line)
            }
            return builder.toString()
        }
    }
}