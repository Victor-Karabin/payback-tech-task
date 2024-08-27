package com.payback.di.images

import com.payback.BuildConfig
import com.payback.data.images.api.ImagesApiProvider
import com.payback.data.images.api.ImagesApiProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ImagesApiModule {

    @Provides
    @Singleton
    fun provideImagesApiProvider(): ImagesApiProvider {
        return ImagesApiProviderImpl(BuildConfig.PIXABAY_BASE_URL)
    }
}
