package com.payback.ui.images.list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.payback.ui.images.list.models.ImageItem
import com.payback.ui.theme.PaybackTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ImagesList(
    items: ImmutableList<ImageItem>,
    onClickItem: (ImageItem) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = state
    ) {
        items(
            count = items.size,
            key = { index -> items[index].id }
        ) { index ->
            val item = items[index]

            ImageItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                onClick = { onClickItem(item) },
                item = item
            )

            if (index < items.lastIndex) HorizontalDivider()
        }
    }
}

@Preview
@Composable
private fun PreviewImagesListDark() {
    val items = persistentListOf(
        ImageItem(
            id = 1,
            userName = "John Smitt",
            imageUrl = "https://image.png",
            tags = persistentListOf("Sunny, Summer, Vacation"),
            description = "vacation"
        ),
        ImageItem(
            id = 2,
            userName = "Syaibatulhamdi",
            imageUrl = "https://image.png",
            tags = persistentListOf("beach", "rain", "clouds"),
            description = "rain"
        ),
        ImageItem(
            id = 3,
            userName = "alba1970",
            imageUrl = "https://image.png",
            tags = persistentListOf("bird", "hummingbird", "blue"),
            description = "parrot"
        )
    )

    PaybackTheme(darkTheme = true) {
        ImagesList(items = items,
            onClickItem = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImagesListLight() {
    val items = persistentListOf(
        ImageItem(
            id = 1,
            userName = "John Smitt",
            imageUrl = "https://image.png",
            tags = persistentListOf("Sunny, Summer, Vacation"),
            description = "vacation"
        ),
        ImageItem(
            id = 2,
            userName = "Syaibatulhamdi",
            imageUrl = "https://image.png",
            tags = persistentListOf("beach", "rain", "clouds"),
            description = "rain"
        ),
        ImageItem(
            id = 3,
            userName = "alba1970",
            imageUrl = "https://image.png",
            tags = persistentListOf("bird", "hummingbird", "blue"),
            description = "parrot"
        )
    )

    PaybackTheme(darkTheme = false) {
        ImagesList(items = items,
            onClickItem = {}
        )
    }
}