package com.payback.ui.images.list.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
internal sealed class ImagesListState {

    @Immutable
    internal data object Loading : ImagesListState()

    @Stable
    internal data class Items(val items: ImmutableList<ImageItem>) : ImagesListState()

    @Immutable
    internal data object Empty : ImagesListState()
}
