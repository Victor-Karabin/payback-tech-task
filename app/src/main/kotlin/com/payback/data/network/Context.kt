package com.payback.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build

fun Context.isNetworkConnected(): Boolean {
    return hasTransport(TRANSPORT_CELLULAR) || hasTransport(TRANSPORT_WIFI)
}

private fun Context.hasTransport(transportType: Int): Boolean {
    val manager = this.connectivityManager()

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        manager.getNetworkCapabilities(manager.activeNetwork)?.hasTransport(transportType) ?: false
    } else {
        manager.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }
}

internal fun Context.connectivityManager(): ConnectivityManager {
    val appContext = this.applicationContext
    return appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}