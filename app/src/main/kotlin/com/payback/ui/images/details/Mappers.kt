package com.payback.ui.images.details

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import com.payback.R
import com.payback.domain.images.models.ImageDetails
import com.payback.ui.images.details.models.DetailsNumber
import com.payback.ui.images.details.models.DetailsNumberType
import com.payback.ui.images.details.models.DetailsState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

internal fun ImageDetails.toDetails(): DetailsState.Details {
    return DetailsState.Details(
        imageUrl = this.imageUrl,
        userName = this.userName,
        tags = this.tags.toImmutableList(),
        numbers = persistentListOf(
            DetailsNumber(DetailsNumberType.Likes, this.likes.toString()),
            DetailsNumber(DetailsNumberType.Comments, this.comments.toString()),
            DetailsNumber(DetailsNumberType.Downloads, this.downloads.toString()),
        ),
        description = this.tags.joinToString(", ")
    )
}

@DrawableRes
internal fun DetailsNumberType.toIconRes(): Int {
    return when (this) {
        DetailsNumberType.Likes -> R.drawable.ic_like
        DetailsNumberType.Downloads -> R.drawable.ic_download
        DetailsNumberType.Comments -> R.drawable.ic_comment
    }
}

@Composable
@ReadOnlyComposable
internal fun DetailsNumberType.toDescription(): String {
    val textRes = when (this) {
        DetailsNumberType.Likes -> R.string.common_likes
        DetailsNumberType.Downloads -> R.string.common_downloads
        DetailsNumberType.Comments -> R.string.common_comments
    }

    return stringResource(id = textRes)
}

@Composable
@ReadOnlyComposable
internal fun DetailsState.toTitle(): String {
    return when (val state = this) {
        is DetailsState.Details -> state.userName
        DetailsState.Loading -> stringResource(id = R.string.common_loading)
    }
}
