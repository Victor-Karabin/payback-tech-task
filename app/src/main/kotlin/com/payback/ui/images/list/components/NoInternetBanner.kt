package com.payback.ui.images.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
internal fun NoInternetBanner(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.background(MaterialTheme.colorScheme.tertiary),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_disconnected),
            contentDescription = stringResource(id = R.string.common_no_internet),
            tint = MaterialTheme.colorScheme.onTertiary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = stringResource(id = R.string.common_no_internet),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}

@Preview
@Composable
private fun PreviewNoInternetBannerDark() {
    PaybackTheme(darkTheme = true) {
        NoInternetBanner(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewNoInternetBannerLight() {
    PaybackTheme(darkTheme = false) {
        NoInternetBanner(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
        )
    }
}
