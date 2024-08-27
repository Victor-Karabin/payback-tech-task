package com.payback.ui.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.payback.R
import com.payback.ui.theme.PaybackTheme

@Composable
internal fun ImageByUrlError(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error_circle),
            contentDescription = stringResource(id = R.string.common_error_loading_image),
            tint = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
private fun PreviewImageByUrlErrorDark() {
    PaybackTheme(darkTheme = true) {
        ImageByUrlError(
            modifier = Modifier.size(64.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewImageByUrlErrorLight() {
    PaybackTheme(darkTheme = false) {
        ImageByUrlError(
            modifier = Modifier.size(64.dp)
        )
    }
}
