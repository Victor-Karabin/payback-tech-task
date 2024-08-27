package com.payback.ui.images.details.models

import androidx.compose.runtime.Stable

@Stable
internal data class DetailsNumber(
    val type: DetailsNumberType,
    val number: String
)