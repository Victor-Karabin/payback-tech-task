package com.payback.data.images.models

internal data class SearchResults(
    val total: Int,
    val totalHits: Int,
    val hits: List<Hit>
)
