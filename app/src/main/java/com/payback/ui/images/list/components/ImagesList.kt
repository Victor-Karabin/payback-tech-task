package com.payback.ui.images.list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.payback.ui.images.list.models.ImageItem
import kotlinx.collections.immutable.ImmutableList

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
                    .height(64.dp),
                onClick = { onClickItem(item) },
                item = item
            )

            if (index <= items.lastIndex) HorizontalDivider()
        }
    }
}