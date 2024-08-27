package com.payback.data.images

import com.payback.data.images.models.Hit
import com.payback.data.images.models.SearchResults

internal interface SearchResultsStore {

    suspend fun store(query: String, results: SearchResults)

    suspend fun results(query: String): SearchResults?

    suspend fun hit(imageId: Int): Hit?
}
