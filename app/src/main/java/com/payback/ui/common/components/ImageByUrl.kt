package com.payback.ui.common.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.payback.ui.theme.PaybackTheme

@Composable
internal fun ImageByUrl(
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    error: @Composable () -> Unit = { ImageByUrlError(modifier = modifier) },
    loading: @Composable () -> Unit = { ImageByUrlLoading(modifier = modifier) },
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
) {
    if (LocalInspectionMode.current) {
        PreviewStub(
            modifier = modifier,
            text = "Image"
        )
    } else {
        SubcomposeAsyncImage(
            modifier = modifier,
            model = imageUrl,
            alignment = alignment,
            error = { error() },
            loading = { loading() },
            contentScale = contentScale,
            alpha = alpha,
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
private fun PreviewImageByUrlDark() {
    PaybackTheme(darkTheme = true) {
        ImageByUrl(
            modifier = Modifier.size(128.dp),
            imageUrl = "https://image.png",
            contentDescription = "image"
        )
    }
}

@Preview
@Composable
private fun PreviewImageByUrlLight() {
    PaybackTheme(darkTheme = false) {
        ImageByUrl(
            modifier = Modifier.size(128.dp),
            imageUrl = "https://image.png",
            contentDescription = "image"
        )
    }
}
