package com.payback.ui.images.list.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.payback.R
import com.payback.ui.images.list.toAlertText
import com.payback.ui.theme.PaybackTheme
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
internal fun ApiLimitExceededDialog(
    tryAgainIn: Duration,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surface,
        text = {
            Text(
                text = stringResource(
                    id = R.string.list_images_api_exceed,
                    tryAgainIn.toAlertText()
                ),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        confirmButton = {
            Button(onClick = onDismissRequest) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        }
    )
}

@Preview
@Composable
private fun PreviewApiLimitExceededDialogDark() {
    PaybackTheme(darkTheme = true) {
        ApiLimitExceededDialog(
            tryAgainIn = 20.toDuration(DurationUnit.SECONDS),
            onDismissRequest = {}
        )
    }
}

@Preview
@Composable
private fun PreviewApiLimitExceededDialogLight() {
    PaybackTheme(darkTheme = false) {
        ApiLimitExceededDialog(
            tryAgainIn = 20.toDuration(DurationUnit.SECONDS),
            onDismissRequest = {}
        )
    }
}
