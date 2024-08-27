package com.payback.ui.images.list.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.payback.R
import com.payback.ui.theme.PaybackTheme

@Composable
internal fun ConfirmDetailsDialog(
    onDismissRequest: () -> Unit,
    onClickConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surface,
        text = {
            Text(
                text = stringResource(id = R.string.list_images_confirm_details),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        confirmButton = {
            Button(onClick = onClickConfirm) {
                Text(text = stringResource(id = android.R.string.ok))
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = stringResource(id = android.R.string.cancel))
            }
        }
    )
}

@Preview
@Composable
private fun PreviewConfirmDetailsDialogDark() {
    PaybackTheme(darkTheme = true) {
        ConfirmDetailsDialog(
            onClickConfirm = {},
            onDismissRequest = {}
        )
    }
}

@Preview
@Composable
private fun PreviewConfirmDetailsDialogLight() {
    PaybackTheme(darkTheme = false) {
        ConfirmDetailsDialog(
            onClickConfirm = {},
            onDismissRequest = {}
        )
    }
}