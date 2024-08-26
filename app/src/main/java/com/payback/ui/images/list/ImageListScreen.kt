package com.payback.ui.images.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.payback.domain.network.NetworkStatus
import com.payback.ui.images.list.components.ImagesList
import com.payback.ui.images.list.components.NoInternetBanner
import com.payback.ui.images.list.models.ImageItem
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ImageListScreen(
    viewModel: ImageListViewModel,
    onNavigateDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

}

@Composable
private fun ImageListScreen(
    networkStatus: NetworkStatus,
    searchQuery: String,
    items: ImmutableList<ImageItem>,
    onClickItem: (ImageItem) -> Unit,
    onChangeSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AnimatedVisibility(visible = !networkStatus.connected) {
            NoInternetBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
            )
        }

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            shape = CircleShape,
            value = searchQuery,
            onValueChange = onChangeSearch,
            textStyle = MaterialTheme.typography.labelMedium,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Search
            ),
            singleLine = true
        )

        ImagesList(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            items = items,
            onClickItem = onClickItem
        )
    }
}

