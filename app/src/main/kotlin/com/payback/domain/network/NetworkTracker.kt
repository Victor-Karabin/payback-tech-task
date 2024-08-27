package com.payback.domain.network

import kotlinx.coroutines.flow.Flow

interface NetworkTracker {

    val isConnected: Flow<NetworkStatus>
}
