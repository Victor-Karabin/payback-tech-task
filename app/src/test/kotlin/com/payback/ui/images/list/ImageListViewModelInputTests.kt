package com.payback.ui.images.list

import com.payback.common.MainCoroutineRule
import com.payback.domain.images.ImagesRepo
import com.payback.domain.network.NetworkStatus
import com.payback.domain.network.NetworkTracker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.net.URLEncoder

@OptIn(ExperimentalCoroutinesApi::class)
class ImageListViewModelInputTests {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private companion object {
        private const val MAX_SEARCH_LENGTH = 100
    }

    private val repo by lazy {
        mock<ImagesRepo> {
            onBlocking { this.search(any()) }
                .doReturn(Result.success(emptyList()))
        }
    }

    private val viewModel by lazy {
        val networkTracker = object : NetworkTracker {
            override val isConnected: Flow<NetworkStatus> = flow { }
        }

        ImageListViewModel(repo, networkTracker)
    }

    @Test
    fun `given encoded search query exceeding max length when input then ignored`() = runTest {
        val longQuery = "a".repeat(MAX_SEARCH_LENGTH + 1)
        val encodedQuery = URLEncoder.encode(longQuery, Charsets.UTF_8.name())
        assert(encodedQuery.length > MAX_SEARCH_LENGTH)
        advanceUntilIdle()

        viewModel.onChangeSearch(longQuery)
        advanceUntilIdle()

        verify(repo, times(1)).search("fruits") // initial only
    }

    @Test
    fun `given encoded search query with max length when input then accepted`() = runTest {
        val query = "a".repeat(MAX_SEARCH_LENGTH)
        val encodedQuery = URLEncoder.encode(query, Charsets.UTF_8.name())
        assert(encodedQuery.length == MAX_SEARCH_LENGTH)
        advanceUntilIdle()

        viewModel.onChangeSearch(query)
        advanceUntilIdle()

        verify(repo, times(1)).search(query)
    }

    @Test
    fun `given encoded search query below max length when input then accepted`() = runTest {
        val query = "a".repeat(MAX_SEARCH_LENGTH -1)
        val encodedQuery = URLEncoder.encode(query, Charsets.UTF_8.name())
        assert(encodedQuery.length < MAX_SEARCH_LENGTH)
        advanceUntilIdle()

        viewModel.onChangeSearch(query)
        advanceUntilIdle()

        verify(repo, times(1)).search(query)
    }
}
