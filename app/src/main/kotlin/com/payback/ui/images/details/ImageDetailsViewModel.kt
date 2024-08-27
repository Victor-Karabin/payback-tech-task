package com.payback.ui.images.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payback.domain.images.ImagesRepo
import com.payback.ui.images.details.models.DetailsState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ImageDetailsViewModel.Factory::class)
class ImageDetailsViewModel @AssistedInject constructor(
    @Assisted private val imageId: Int,
    private val repo: ImagesRepo
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(@Assisted imageId: Int): ImageDetailsViewModel
    }

    private val unknownErrorChannel = Channel<Throwable>(capacity = Channel.BUFFERED)
    internal val unknownError: Flow<Throwable>
        get() = unknownErrorChannel.receiveAsFlow()

    private val mutableState = MutableStateFlow<DetailsState>(DetailsState.Loading)
    internal val state = mutableState.asStateFlow()

    internal fun refreshDetails() {
        viewModelScope.launch {
            mutableState.value = DetailsState.Loading

            repo.details(imageId)
                .onSuccess { imageDetails ->
                    mutableState.value = imageDetails.toDetails()
                }
                .onFailure { ex: Throwable ->
                    unknownErrorChannel.send(ex)
                    Log.d(TAG, "fetch details by id: $state failed", ex)
                }
        }
    }

    private companion object {
        private val TAG = ImageDetailsViewModel::class.java.name
    }
}