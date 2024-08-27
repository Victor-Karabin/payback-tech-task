package com.payback

import android.app.Application
import android.util.Log
import coil.EventListener
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.size.Precision
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    override fun onLowMemory() {
        this.cacheDir.resolve(IMAGE_CACHE).deleteRecursively()
        super.onLowMemory()
    }

    override fun newImageLoader(): ImageLoader {
        val diskCache = DiskCache.Builder()
            .directory(this.cacheDir.resolve(IMAGE_CACHE))
            .build()

        val eventListener = object : EventListener {
            override fun onError(request: ImageRequest, result: ErrorResult) {
                super.onError(request, result)
                val ex = IllegalStateException("loading failed ${request.data}", result.throwable)

                // TODO analytics
                Log.d("COIL", "loading image is failed", ex)
            }
        }

        return ImageLoader.Builder(this)
            .eventListener(eventListener)
            .diskCache(diskCache)
            .precision(Precision.INEXACT)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .networkObserverEnabled(true)
            .build()
    }

    companion object {
        private const val IMAGE_CACHE = "image_cache"
    }
}
