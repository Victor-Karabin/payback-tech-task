package com.payback.data.images

import com.payback.boundary.common.errors.NetworkDisconnected
import com.payback.boundary.images.ImagesRepo
import com.payback.boundary.images.errors.ApiLimitExceeded
import com.payback.boundary.images.errors.ImageNotFound
import com.payback.boundary.images.models.Image
import com.payback.boundary.images.models.ImageDetails
import com.payback.commons.mapFailure
import com.payback.data.rest.RestThrowable
import com.payback.data.rest.wrapRequest
import com.payback.data.toImageDetails
import com.payback.data.toImages
import com.payback.di.coroutines.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import java.net.UnknownHostException
import javax.inject.Inject

internal class ImagesRepoImpl @Inject constructor(
    private val apiKey: String,
    private val api: ImagesApi,
    private val cache: SearchResultsStore,
    @IODispatcher
    private val io: CoroutineDispatcher
) : ImagesRepo {

    override suspend fun search(query: String): Result<List<Image>> {
        val cached = cache.results(query)
        if (cached != null) return Result.success(cached.toImages())

        return wrapRequest(io) { api.search(apiKey, query) }
            .mapFailure { ex: Throwable ->
                val throwable = when {
                    ex is UnknownHostException -> NetworkDisconnected()
                    ex is RestThrowable && ex.code == TOO_MANY_REQUESTS -> ApiLimitExceeded()
                    else -> ex
                }

                Result.failure(throwable)
            }
            .map { searchResults ->
                cache.store(query, searchResults)
                searchResults.toImages()
            }
    }

    override suspend fun details(imageId: Int): Result<ImageDetails> {
        val cached = cache.hit(imageId)

        return if (cached != null) {
            Result.success(cached.toImageDetails())
        } else {
            Result.failure(ImageNotFound(imageId))
        }
    }

    private companion object {
        private const val TOO_MANY_REQUESTS = 429
    }
}