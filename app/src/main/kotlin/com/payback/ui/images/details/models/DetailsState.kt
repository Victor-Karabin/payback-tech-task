package com.payback.ui.images.details.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
internal sealed class DetailsState {

    @Immutable
    internal data object Loading : DetailsState()

    @Stable
    internal data class Details(
        val imageUrl: String,
        val userName: String,
        val tags: ImmutableList<String>,
        val numbers: ImmutableList<DetailsNumber>,
        val description: String
    ) : DetailsState()
}
