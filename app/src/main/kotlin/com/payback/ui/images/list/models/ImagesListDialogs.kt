package com.payback.ui.images.list.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlin.time.Duration

@Stable
internal sealed class ImagesListDialogs {

    @Stable
    data class ApiLimit(val resetDelay: Duration) : ImagesListDialogs()

    @Stable
    data class ConfirmDetails(val imageId: Int) : ImagesListDialogs()

    @Immutable
    data object None : ImagesListDialogs()
}