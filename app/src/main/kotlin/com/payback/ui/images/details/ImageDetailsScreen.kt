package com.payback.ui.images.details

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.payback.R
import com.payback.ui.common.SingleEventEffect
import com.payback.ui.common.components.ImageByUrl
import com.payback.ui.common.components.ImageTags
import com.payback.ui.common.components.Loading
import com.payback.ui.images.details.components.DetailsNumberRow
import com.payback.ui.images.details.models.DetailsNumber
import com.payback.ui.images.details.models.DetailsNumberType
import com.payback.ui.images.details.models.DetailsState
import com.payback.ui.theme.PaybackTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ImageDetailsScreen(
    viewModel: ImageDetailsViewModel,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.refreshDetails()
    }

    val context = LocalContext.current
    val unknownError = stringResource(id = R.string.common_error_occurred)

    SingleEventEffect(sideEffectFlow = viewModel.unknownError) {
        onClickBack()
        Toast.makeText(context, unknownError, Toast.LENGTH_LONG).show()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    ImageDetailsScreen(
        modifier = modifier,
        state = state,
        onClickBack = onClickBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageDetailsScreen(
    state: DetailsState,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.toTitle(),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onClickBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.common_back)
                        )
                    }
                }
            )
        }
    ) { padding: PaddingValues ->

        when (state) {
            is DetailsState.Details -> ImageDetailsScreen(
                modifier = Modifier.padding(padding),
                state = state
            )

            DetailsState.Loading -> Loading(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        }
    }
}

@Composable
private fun ImageDetailsScreen(
    state: DetailsState.Details,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ImageByUrl(
            modifier = Modifier.fillMaxWidth(),
            imageUrl = state.imageUrl,
            contentDescription = state.description,
            contentScale = ContentScale.FillWidth
        )

        Spacer(modifier = Modifier.height(16.dp))

        ImageTags(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            tags = state.tags
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            state.numbers.forEachIndexed { index, number ->
                key(number.type) {
                    DetailsNumberRow(
                        modifier = Modifier.weight(1f),
                        number = number
                    )
                }

                if (index < state.numbers.lastIndex) Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
private fun PreviewImageDetailsScreenDark() {
    PaybackTheme(darkTheme = true) {
        ImageDetailsScreen(
            state = DetailsState.Details(
                userName = "John Smitt",
                imageUrl = "https://image.png",
                tags = persistentListOf("cat", "orange", "cute", "kitty"),
                description = "cat",
                numbers = persistentListOf(
                    DetailsNumber(DetailsNumberType.Likes, "100"),
                    DetailsNumber(DetailsNumberType.Comments, "4"),
                    DetailsNumber(DetailsNumberType.Downloads, "456")
                )
            ),
            onClickBack = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageDetailsScreenLoadingDark() {
    PaybackTheme(darkTheme = true) {
        ImageDetailsScreen(
            state = DetailsState.Loading,
            onClickBack = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageDetailsScreenLight() {
    PaybackTheme(darkTheme = false) {
        ImageDetailsScreen(
            state = DetailsState.Details(
                userName = "John Smitt",
                imageUrl = "https://image.png",
                tags = persistentListOf("cat", "orange", "cute", "kitty"),
                description = "cat",
                numbers = persistentListOf(
                    DetailsNumber(DetailsNumberType.Likes, "100"),
                    DetailsNumber(DetailsNumberType.Comments, "4"),
                    DetailsNumber(DetailsNumberType.Downloads, "456")
                )
            ),
            onClickBack = {}
        )
    }
}

@Preview
@Composable
private fun PreviewImageDetailsScreenLoadingLight() {
    PaybackTheme(darkTheme = false) {
        ImageDetailsScreen(
            state = DetailsState.Loading,
            onClickBack = {}
        )
    }
}
