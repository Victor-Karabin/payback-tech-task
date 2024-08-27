package com.payback.ui.images.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.payback.ui.images.details.models.DetailsNumber
import com.payback.ui.images.details.models.DetailsNumberType
import com.payback.ui.images.details.toDescription
import com.payback.ui.images.details.toIconRes
import com.payback.ui.theme.PaybackTheme

@Composable
internal fun DetailsNumberRow(
    number: DetailsNumber,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = number.type.toIconRes()),
            contentDescription = number.type.toDescription(),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = number.number,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun PreviewDetailsNumberRowLikesDark() {
    PaybackTheme(darkTheme = true) {
        DetailsNumberRow(number = DetailsNumber(DetailsNumberType.Likes, "10"))
    }
}

@Preview
@Composable
private fun PreviewDetailsNumberRowCommentsDark() {
    PaybackTheme(darkTheme = true) {
        DetailsNumberRow(number = DetailsNumber(DetailsNumberType.Comments, "1"))
    }
}

@Preview
@Composable
private fun PreviewDetailsNumberRowDownloadsDark() {
    PaybackTheme(darkTheme = true) {
        DetailsNumberRow(number = DetailsNumber(DetailsNumberType.Downloads, "199"))
    }
}

@Preview
@Composable
private fun PreviewDetailsNumberRowLikesLight() {
    PaybackTheme(darkTheme = false) {
        DetailsNumberRow(number = DetailsNumber(DetailsNumberType.Likes, "10"))
    }
}

@Preview
@Composable
private fun PreviewDetailsNumberRowCommentsLight() {
    PaybackTheme(darkTheme = false) {
        DetailsNumberRow(number = DetailsNumber(DetailsNumberType.Comments, "1"))
    }
}

@Preview
@Composable
private fun PreviewDetailsNumberRowDownloadsLight() {
    PaybackTheme(darkTheme = false) {
        DetailsNumberRow(number = DetailsNumber(DetailsNumberType.Downloads, "199"))
    }
}
