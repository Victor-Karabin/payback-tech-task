package com.payback.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.payback.ui.theme.PaybackTheme

@Composable
internal fun PreviewStub(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 22.sp,
) {
    Box(
        modifier = modifier.background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = fontSize,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun PreviewPreviewStubDark() {
    PaybackTheme(darkTheme = true) {
        PreviewStub(
            modifier = Modifier.size(64.dp),
            text = "STUB"
        )
    }
}

@Preview
@Composable
private fun PreviewPreviewStubLight() {
    PaybackTheme(darkTheme = false) {
        PreviewStub(
            modifier = Modifier.size(64.dp),
            text = "STUB"
        )
    }
}