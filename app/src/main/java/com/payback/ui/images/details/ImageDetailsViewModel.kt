package com.payback.ui.images.details

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = ImageDetailsViewModel.Factory::class)
class ImageDetailsViewModel @AssistedInject constructor(
    @Assisted private val imageId: Int
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(imageId: Int): ImageDetailsViewModel
    }
}