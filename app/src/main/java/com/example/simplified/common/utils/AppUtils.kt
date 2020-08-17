package com.example.simplified.common.utils

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Html
import android.text.Spanned
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.simplified.common.App
import java.io.File
import java.util.*



object AppUtils {

    @SuppressLint("MissingPermission")
    fun isConnectingToInternet(context: Context): Boolean {
        val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo

        return info?.isConnected ?: false
    }


    fun getNetworkClass(context: Context): String {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        if (info == null || !info.isConnected)
            return "NOT_CONNECTED" //not connected
        if (info.type == ConnectivityManager.TYPE_WIFI)
            return "WIFI"
        if (info.type == ConnectivityManager.TYPE_MOBILE) {
            return when (info.subtype) {
                TelephonyManager.NETWORK_TYPE_GPRS, TelephonyManager.NETWORK_TYPE_EDGE, TelephonyManager.NETWORK_TYPE_CDMA, TelephonyManager.NETWORK_TYPE_1xRTT, TelephonyManager.NETWORK_TYPE_IDEN //api<8 : replace by 11
                -> "2G"
                TelephonyManager.NETWORK_TYPE_UMTS, TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, TelephonyManager.NETWORK_TYPE_HSDPA, TelephonyManager.NETWORK_TYPE_HSUPA, TelephonyManager.NETWORK_TYPE_HSPA, TelephonyManager.NETWORK_TYPE_EVDO_B //api<9 : replace by 14
                    , TelephonyManager.NETWORK_TYPE_EHRPD  //api<11 : replace by 12
                    , TelephonyManager.NETWORK_TYPE_HSPAP  //api<13 : replace by 15
                -> "3G"
                TelephonyManager.NETWORK_TYPE_LTE    //api<11 : replace by 13
                -> "4G"
                else -> "OTHERS"
            }
        }
        return "OTHERS"
    }

}