package com.payback.ui.common.components

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.payback.ui.theme.PaybackTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ImageTags(
    tags: ImmutableList<String>,
    modifier: Modifier = Modifier
) {
    FlowRow(modifier = modifier) {
        tags.forEachIndexed { index, tag ->
            ImageTag(
                modifier = Modifier.padding(vertical = 1.dp),
                text = tag
            )

            if (index < tags.lastIndex) Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewImageTagsDark() {
    PaybackTheme(darkTheme = true) {
        ImageTags(tags = persistentListOf("apple", "green", "leaf"))
    }
}

@Preview
@Composable
private fun PreviewImageTagsLight() {
    PaybackTheme(darkTheme = false) {
        ImageTags(tags = persistentListOf("apple", "green", "leaf"))
    }
}
