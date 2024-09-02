package com.payback.ui.images.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payback.commons.mapFailure
import com.payback.domain.images.ImagesRepo
import com.payback.domain.images.errors.ApiLimitExceeded
import com.payback.domain.network.NetworkDisconnected
import com.payback.domain.network.NetworkStatus
import com.payback.domain.network.NetworkTracker
import com.payback.ui.images.list.models.ImageItem
import com.payback.ui.images.list.models.ImagesListDialogs
import com.payback.ui.images.list.models.ImagesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repo: ImagesRepo,
    networkTracker: NetworkTracker
) : ViewModel() {

    private val mutableSearch = MutableStateFlow(INITIAL_SEARCH_QUERY)
    internal val search = mutableSearch.asStateFlow()

    internal val network = networkTracker.isConnected
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = NetworkStatus(true))

    private val mutableDialogs = MutableStateFlow<ImagesListDialogs>(ImagesListDialogs.None)
    internal val dialogs = mutableDialogs.asStateFlow()

    private val unknownErrorChannel = Channel<Throwable>(capacity = Channel.BUFFERED)
    internal val unknownError: Flow<Throwable>
        get() = unknownErrorChannel.receiveAsFlow()

    @OptIn(FlowPreview::class)
    internal val state: StateFlow<ImagesListState> =
        network.combine(search.debounce(SEARCH_DEBOUNCE)) { _, query ->
            repo.search(encode(query))
                .map { images ->
                    val items = images.map { image -> image.toImageItem() }.toPersistentList()
                    if (items.isEmpty()) ImagesListState.Empty else ImagesListState.Items(items)
                }
                .mapFailure { ex: Throwable ->
                    if (ex is NetworkDisconnected) {
                        Result.success(ImagesListState.Empty)
                    } else {
                        Result.failure(ex)
                    }
                }
                .onFailure { ex: Throwable ->
                    when (ex) {
                        is ApiLimitExceeded -> {
                            mutableDialogs.value = ImagesListDialogs.ApiLimit(ex.resetDelay)
                        }

                        else -> unknownErrorChannel.send(ex)
                    }

                    Log.d(TAG, "search by $query is failed", ex)
                }.getOrNull()
        }.filterNotNull()
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = ImagesListState.Loading)

    internal fun hideDialog() {
        mutableDialogs.value = ImagesListDialogs.None
    }

    internal fun onChangeSearch(query: String) {
        val encoded = encode(query)
        if (encoded.length <= MAX_SEARCH_LENGTH) mutableSearch.value = query
    }

    internal fun onClickItem(item: ImageItem) {
        mutableDialogs.value = ImagesListDialogs.ConfirmDetails(item.id)
    }

    private fun encode(query: String): String {
        return URLEncoder.encode(query, Charsets.UTF_8.name())
    }

    private companion object {
        private val TAG = ImageListViewModel::class.java.name
        private const val SEARCH_DEBOUNCE = 500L // ms
        private const val MAX_SEARCH_LENGTH = 100
        private const val INITIAL_SEARCH_QUERY = "fruits"
    }
}
