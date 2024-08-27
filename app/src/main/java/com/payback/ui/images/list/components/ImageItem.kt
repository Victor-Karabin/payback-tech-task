package com.payback.ui.images.list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.payback.ui.common.components.ImageByUrl
import com.payback.ui.common.components.ImageTag
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
        modifier = modifier
            .clickable(onClick = onClick)
            .background(color = MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageByUrl(
            modifier = Modifier.size(72.dp),
            imageUrl = item.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = item.description
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = item.userName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            FlowRow(modifier = Modifier.fillMaxWidth()) {
                item.tags.forEachIndexed { index, tag ->
                    ImageTag(
                        modifier = Modifier.padding(vertical = 1.dp),
                        text = tag
                    )

                    if (index < item.tags.lastIndex) Spacer(modifier = Modifier.width(4.dp))
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
                .height(72.dp),
            item = ImageItem(
                id = 1,
                userName = "Jonh Smitt",
                imageUrl = "https://image.png",
                tags = persistentListOf(
                    "sunrise",
                    "beach",
                    "paper art",
                    "flowers",
                    "yellow",
                    "pomegranate"
                ),
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
                .height(72.dp),
            item = ImageItem(
                id = 1,
                userName = "Jonh Smitt",
                imageUrl = "https://image.png",
                tags = persistentListOf(
                    "sunrise",
                    "beach",
                    "paper art",
                    "flowers",
                    "yellow",
                    "pomegranate"
                ),
                description = "sunrise, beach, paper art"
            ),
            onClick = {}
        )
    }
}