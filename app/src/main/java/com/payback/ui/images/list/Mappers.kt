package com.payback.ui.images.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.pluralStringResource
import com.payback.R
import com.payback.domain.images.models.Image
import com.payback.ui.images.list.models.ImageItem
import kotlinx.collections.immutable.toImmutableList
import kotlin.time.Duration

internal fun Image.toImageItem(): ImageItem {
    return ImageItem(
        id = this.id,
        imageUrl = this.thumbnailUrl,
        userName = this.userName,
        tags = this.tags.toImmutableList(),
        description = this.tags.joinToString(", ")
    )
}

@Composable
@ReadOnlyComposable
internal fun Duration.toAlertText(): String {
    val pair = when {
        this.inWholeMinutes <= 0 -> R.plurals.seconds_short to this.inWholeSeconds.toInt()
        this.inWholeHours <= 0 -> R.plurals.minutes_short to this.inWholeMinutes.toInt()
        this.inWholeDays <= 0 -> R.plurals.hours to this.inWholeHours.toInt()
        else -> R.plurals.days to this.inWholeDays.toInt()
    }

    return "${pair.second} ${pluralStringResource(id = pair.first, count = pair.second)}"
}