package com.payback.boundary.images

import com.payback.boundary.images.models.Image
import com.payback.boundary.images.models.ImageDetails

interface ImagesRepo {

    suspend fun search(query: String): Result<List<Image>>

    suspend fun details(imageId: Int): Result<ImageDetails>
}