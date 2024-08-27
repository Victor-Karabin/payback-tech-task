package com.payback.domain.images.models

data class ImageDetails(
    val id: Int,
    val imageUrl: String,
    val userName: String,
    val tags: List<String>,
    val likes: Int,
    val downloads: Int,
    val comments: Int
)
