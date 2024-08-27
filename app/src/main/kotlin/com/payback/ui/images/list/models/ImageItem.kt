package com.payback.ui.images.list.models

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
internal data class ImageItem(
    val id: Int,
    val imageUrl: String,
    val userName: String,
    val tags: ImmutableList<String>,
    val description: String
)
