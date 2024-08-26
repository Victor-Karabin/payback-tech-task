package com.payback.domain.images.models

data class Image(
    val id: Int,
    val thumbnailUrl: String,
    val userName: String,
    val tags: List<String>
)