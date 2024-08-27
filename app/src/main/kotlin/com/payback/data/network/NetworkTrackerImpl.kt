package com.payback.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.payback.domain.network.NetworkStatus
import com.payback.domain.network.NetworkTracker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class NetworkTrackerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkTracker {

    private val networkRequest by lazy {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }

    override val isConnected: Flow<NetworkStatus> = callbackFlow {
        val manager = context.connectivityManager()

        val callback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                // status should be requested each time. sometimes it returns wrong value
                trySend(NetworkStatus(context.isNetworkConnected()))
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                // status should be requested each time. sometimes it returns wrong value
                trySend(NetworkStatus(context.isNetworkConnected()))
            }

            override fun onUnavailable() {
                super.onUnavailable()
                // status should be requested each time. sometimes it returns wrong value
                trySend(NetworkStatus(context.isNetworkConnected()))
            }
        }

        manager.registerNetworkCallback(networkRequest, callback)

        awaitClose { manager.unregisterNetworkCallback(callback) }
    }
        .distinctUntilChanged()
        .onStart { this.emit(NetworkStatus(context.isNetworkConnected())) }
}
