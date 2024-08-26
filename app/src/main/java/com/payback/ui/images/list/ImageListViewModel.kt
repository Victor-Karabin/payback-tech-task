package com.payback.ui.images.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payback.domain.network.NetworkDisconnected
import com.payback.domain.images.ImagesRepo
import com.payback.domain.images.errors.ApiLimitExceeded
import com.payback.domain.images.models.Image
import com.payback.domain.network.NetworkStatus
import com.payback.domain.network.NetworkTracker
import com.payback.ui.images.list.models.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repo: ImagesRepo,
    private val networkTracker: NetworkTracker
) : ViewModel() {

    private val mutableSearch = MutableStateFlow(INITIAL_SEARCH_QUERY)
    internal val search = mutableSearch.asStateFlow()

    private val mutableItems = MutableStateFlow(persistentListOf<ImageItem>())
    internal val items = mutableItems.asStateFlow()

    internal val networkStatus = networkTracker.isConnected
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = NetworkStatus(true))

    private val mutableApiLimitExceeded = MutableStateFlow(false)
    internal val apiLimitExceeded = mutableApiLimitExceeded.asStateFlow()

    private val unknownErrorChannel = Channel<Throwable>(capacity = Channel.BUFFERED)
    internal val unknownError: Flow<Throwable>
        get() = unknownErrorChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            networkStatus.collect { status -> if (status.connected) search(search.value) }
        }
    }

    internal fun search(query: String) {
        mutableSearch.value = query

        if (networkStatus.value.connected) {
            viewModelScope.launch {
                repo.search(query)
                    .onSuccess { images: List<Image> ->
                        mutableItems.value = images.map { image -> image.toImageItem() }
                            .toPersistentList()
                    }
                    .onFailure { ex: Throwable ->
                        when (ex) {
                            is ApiLimitExceeded -> mutableApiLimitExceeded.value = true
                            is NetworkDisconnected -> Unit // do nothing
                            else -> unknownErrorChannel.send(ex)
                        }

                        Log.d(TAG, "search by $query is failed", ex)
                    }
            }
        }
    }

    private companion object {
        private val TAG = ImageListViewModel::class.java.name

        private const val INITIAL_SEARCH_QUERY = "fruits"
    }
}