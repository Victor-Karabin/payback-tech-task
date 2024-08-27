package com.payback.ui.images.list.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlin.time.Duration

@Stable
internal sealed class ImagesListDialogs {

    @Stable
    internal data class ApiLimit(val resetDelay: Duration) : ImagesListDialogs()

    @Stable
    internal data class ConfirmDetails(val imageId: Int) : ImagesListDialogs()

    @Immutable
    internal data object None : ImagesListDialogs()
}
