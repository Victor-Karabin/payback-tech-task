package com.payback.data.images

import com.payback.data.images.models.Hit
import com.payback.data.images.models.SearchResults
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Hashtable
import javax.inject.Inject

internal class SearchResultsStoreImpl @Inject constructor() : SearchResultsStore {

    private val resultsByQuery = Hashtable<String, SearchResults>()
    private val hitsById = Hashtable<Int, Hit>()
    private val mutex = Mutex()

    override suspend fun store(query: String, results: SearchResults) {
        mutex.withLock {
            resultsByQuery[query] = results
            results.hits.forEach { hit -> hitsById[hit.id] = hit }
        }
    }

    override suspend fun results(query: String): SearchResults? {
        return resultsByQuery[query]
    }

    override suspend fun hit(hitId: Int): Hit? {
        return hitsById[hitId]
    }
}