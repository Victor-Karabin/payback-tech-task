package com.payback.data.images

import com.payback.commons.mapFailure
import com.payback.data.images.api.ImagesApi
import com.payback.data.rest.RestThrowable
import com.payback.data.rest.wrapRequest
import com.payback.di.coroutines.IODispatcher
import com.payback.domain.images.ImagesRepo
import com.payback.domain.images.errors.ApiLimitExceeded
import com.payback.domain.images.errors.ImageNotFound
import com.payback.domain.images.models.Image
import com.payback.domain.images.models.ImageDetails
import com.payback.domain.network.NetworkDisconnected
import kotlinx.coroutines.CoroutineDispatcher
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

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
                    ex is RestThrowable && ex.code == TOO_MANY_REQUESTS_CODE -> {
                        val reset = ex.headers[RESET_DELAY]?.toIntOrNull() ?: 0
                        ApiLimitExceeded(reset.toDuration(DurationUnit.SECONDS))
                    }

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
        private const val TOO_MANY_REQUESTS_CODE = 429
        private const val RESET_DELAY = "X-RateLimit-Reset"
    }
}