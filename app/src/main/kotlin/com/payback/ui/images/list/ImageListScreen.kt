package com.payback.ui.images.list

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.payback.R
import com.payback.domain.network.NetworkStatus
import com.payback.ui.common.SingleEventEffect
import com.payback.ui.common.components.Loading
import com.payback.ui.images.list.components.ApiLimitExceededDialog
import com.payback.ui.images.list.components.ConfirmDetailsDialog
import com.payback.ui.images.list.components.EmptyImagesList
import com.payback.ui.images.list.components.ImagesList
import com.payback.ui.images.list.components.NoInternetBanner
import com.payback.ui.images.list.models.ImageItem
import com.payback.ui.images.list.models.ImagesListDialogs
import com.payback.ui.images.list.models.ImagesListState
import com.payback.ui.theme.PaybackTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ImageListScreen(
    viewModel: ImageListViewModel,
    onNavigateDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val unknownError = stringResource(id = R.string.common_error_occurred)

    SingleEventEffect(sideEffectFlow = viewModel.unknownError) {
        Toast.makeText(context, unknownError, Toast.LENGTH_LONG).show()
    }

    val dialog by viewModel.dialogs.collectAsStateWithLifecycle()
    when (val type = dialog) {
        is ImagesListDialogs.ApiLimit -> ApiLimitExceededDialog(
            tryAgainIn = type.resetDelay,
            onDismissRequest = viewModel::hideDialog
        )

        is ImagesListDialogs.ConfirmDetails -> ConfirmDetailsDialog(
            onClickConfirm = {
                viewModel.hideDialog()
                onNavigateDetails(type.imageId)
            },
            onDismissRequest = viewModel::hideDialog
        )

        ImagesListDialogs.None -> Unit // do nothing
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchQuery by viewModel.search.collectAsStateWithLifecycle()
    val networkStatus by viewModel.network.collectAsStateWithLifecycle()

    ImageListScreen(
        modifier = modifier,
        networkStatus = networkStatus,
        searchQuery = searchQuery,
        state = state,
        onClickItem = viewModel::onClickItem,
        onChangeSearch = viewModel::onChangeSearch
    )
}

@Composable
private fun ImageListScreen(
    networkStatus: NetworkStatus,
    searchQuery: String,
    state: ImagesListState,
    onClickItem: (ImageItem) -> Unit,
    onChangeSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        AnimatedVisibility(visible = !networkStatus.connected) {
            NoInternetBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
            )
        }

        val textColor = MaterialTheme.colorScheme.onBackground
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = CircleShape,
            value = searchQuery,
            onValueChange = onChangeSearch,
            textStyle = MaterialTheme.typography.labelMedium.copy(color = textColor),
            singleLine = true
        )

        when (state) {
            ImagesListState.Empty -> EmptyImagesList(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            is ImagesListState.Items -> ImagesList(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                items = state.items,
                onClickItem = onClickItem
            )

            ImagesListState.Loading -> Loading(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewImageListScreenDark() {
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
        ImageListScreen(
            networkStatus = NetworkStatus(connected = false),
            searchQuery = "bird",
            state = ImagesListState.Items(items),
            onClickItem = {},
            onChangeSearch = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageListScreenLight() {
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
        ImageListScreen(
            networkStatus = NetworkStatus(connected = false),
            searchQuery = "bird",
            state = ImagesListState.Items(items),
            onClickItem = {},
            onChangeSearch = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageListScreenEmptyDark() {
    PaybackTheme(darkTheme = true) {
        ImageListScreen(
            networkStatus = NetworkStatus(connected = true),
            searchQuery = "bird",
            state = ImagesListState.Empty,
            onClickItem = {},
            onChangeSearch = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageListScreenEmptyLight() {
    PaybackTheme(darkTheme = false) {
        ImageListScreen(
            networkStatus = NetworkStatus(connected = true),
            searchQuery = "bird",
            state = ImagesListState.Empty,
            onClickItem = {},
            onChangeSearch = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageListScreenLoadingDark() {
    PaybackTheme(darkTheme = true) {
        ImageListScreen(
            networkStatus = NetworkStatus(connected = true),
            searchQuery = "bird",
            state = ImagesListState.Loading,
            onClickItem = {},
            onChangeSearch = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageListScreenLoadingLight() {
    PaybackTheme(darkTheme = false) {
        ImageListScreen(
            networkStatus = NetworkStatus(connected = true),
            searchQuery = "bird",
            state = ImagesListState.Loading,
            onClickItem = {},
            onChangeSearch = {}
        )
    }
}
