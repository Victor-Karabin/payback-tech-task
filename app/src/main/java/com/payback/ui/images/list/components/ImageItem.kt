package com.payback.ui.images.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.payback.ui.components.ImageByUrl
import com.payback.ui.components.ImageTag
import com.payback.ui.images.list.models.ImageItem
import com.payback.ui.theme.PaybackTheme
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ImageItem(
    item: ImageItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageByUrl(
            modifier = Modifier.size(64.dp),
            imageUrl = item.imageUrl,
            contentDescription = item.description
        )

        VerticalDivider(modifier = Modifier.fillMaxHeight())

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = item.userName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            FlowRow(modifier = Modifier.fillMaxWidth()) {
                item.tags.forEachIndexed { index, tag ->
                    ImageTag(text = tag)

                    if (index <= item.tags.lastIndex) Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewImageItemDark() {
    PaybackTheme(darkTheme = true) {
        ImageItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            item = ImageItem(
                id = 1,
                userName = "Jonh Smitt",
                imageUrl = "https://image.png",
                tags = persistentListOf("sunrise", "beach", "paper art"),
                description = "sunrise, beach, paper art"
            ),
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageItemLight() {
    PaybackTheme(darkTheme = false) {
        ImageItem(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            item = ImageItem(
                id = 1,
                userName = "Jonh Smitt",
                imageUrl = "https://image.png",
                tags = persistentListOf("sunrise", "beach", "paper art"),
                description = "sunrise, beach, paper art"
            ),
            onClick = {}
        )
    }
}