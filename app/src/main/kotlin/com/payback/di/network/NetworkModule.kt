package com.payback.di.network

import com.payback.data.network.NetworkTrackerImpl
import com.payback.domain.network.NetworkTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {

    @Binds
    @Singleton
    fun bindNetworkTracker(tracker: NetworkTrackerImpl): NetworkTracker
}
