package com.payback.ui.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.payback.ui.theme.PaybackTheme

@Composable
internal fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun PreviewLoadingDark() {
    PaybackTheme(darkTheme = true) {
        Loading()
    }
}

@Preview
@Composable
private fun PreviewLoadingLight() {
    PaybackTheme(darkTheme = false) {
        Loading()
    }
}