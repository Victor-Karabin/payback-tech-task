package com.payback.di.images

import com.payback.data.images.SearchResultsStore
import com.payback.data.images.SearchResultsStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ImagesCacheModule {

    @Binds
    @Singleton
    fun bindSearchResultsStore(store: SearchResultsStoreImpl): SearchResultsStore
}