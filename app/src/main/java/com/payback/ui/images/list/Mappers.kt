package com.payback.ui.images.list

import com.payback.domain.images.models.Image
import com.payback.ui.images.list.models.ImageItem
import kotlinx.collections.immutable.toImmutableList

internal fun Image.toImageItem(): ImageItem {
    return ImageItem(
        id = this.id,
        imageUrl = this.thumbnailUrl,
        userName = this.userName,
        tags = this.tags.toImmutableList(),
        description = this.tags.joinToString(", ")
    )
}