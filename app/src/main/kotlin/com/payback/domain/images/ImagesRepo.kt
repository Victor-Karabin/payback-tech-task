package com.payback.domain.images

import com.payback.domain.images.models.Image
import com.payback.domain.images.models.ImageDetails

interface ImagesRepo {

    suspend fun search(query: String): Result<List<Image>>

    suspend fun details(imageId: Int): Result<ImageDetails>
}