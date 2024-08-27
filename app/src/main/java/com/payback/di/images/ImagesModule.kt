package com.payback.di.images

import com.payback.BuildConfig
import com.payback.data.images.ImagesRepoImpl
import com.payback.data.images.SearchResultsStore
import com.payback.data.images.api.ImagesApiProvider
import com.payback.di.coroutines.IODispatcher
import com.payback.domain.images.ImagesRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
internal object ImagesModule {

    @Provides
    @ViewModelScoped
    fun provideImagesRepo(
        apiProvider: ImagesApiProvider,
        cache: SearchResultsStore,
        @IODispatcher
        io: CoroutineDispatcher
    ): ImagesRepo {
        return ImagesRepoImpl(BuildConfig.PIXABAY_KEY, apiProvider.provideImagesApi(), cache, io)
    }
}