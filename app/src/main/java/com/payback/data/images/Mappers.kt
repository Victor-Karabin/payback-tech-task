package com.payback.data.images

import com.payback.data.images.models.Hit
import com.payback.data.images.models.SearchResults
import com.payback.domain.images.models.Image
import com.payback.domain.images.models.ImageDetails

internal fun SearchResults.toImages(): List<Image> {
    return this.hits.map { hit -> hit.toImage() }
}

private fun Hit.toImage(): Image {
    return Image(
        id = this.id,
        thumbnailUrl = this.previewURL,
        userName = this.user,
        tags = this.tags.split(",")
    )
}

internal fun Hit.toImageDetails(): ImageDetails {
    return ImageDetails(
        id = this.id,
        imageUrl = this.largeImageURL,
        userName = this.user,
        tags = this.tags,
        likes = this.likes,
        downloads = this.downloads,
        comments = this.comments
    )
}