package com.payback.ui.images.list

import app.cash.turbine.test
import com.payback.common.MainCoroutineRule
import com.payback.domain.images.ImagesRepo
import com.payback.domain.images.models.Image
import com.payback.domain.network.NetworkStatus
import com.payback.domain.network.NetworkTracker
import com.payback.ui.images.list.models.ImagesListState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

@OptIn(ExperimentalCoroutinesApi::class)
class ImageListViewModelSearchTests {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Test
    fun `given viewmodel when initial state then search`() = runTest {
        val networkTracker = object : NetworkTracker {
            override val isConnected: Flow<NetworkStatus> = flow { }
        }

        val repo = mock<ImagesRepo> {
            onBlocking { this.search(any()) }
                .doReturn(Result.success(emptyList()))
        }

        ImageListViewModel(repo, networkTracker)

        advanceUntilIdle()
        verify(repo, times(1)).search(any())
    }

    @Test
    fun `given viewmodel when network status changed then search`() = runTest {
        val networkFlow = MutableStateFlow(NetworkStatus(true))
        val networkTracker = mock<NetworkTracker> {
            on { this.isConnected } doReturn networkFlow
        }

        val repo = mock<ImagesRepo> {
            onBlocking { this.search(any()) }
                .doReturn(Result.success(emptyList()))
        }

        ImageListViewModel(repo, networkTracker)
        advanceUntilIdle()
        networkFlow.emit(NetworkStatus(false))
        advanceUntilIdle()
        networkFlow.emit(NetworkStatus(true))
        advanceUntilIdle()
        verify(repo, times(3)).search(any())
    }

    @Test
    fun `given viewmodel when search query changed then search`() = runTest {
        val networkFlow = MutableStateFlow(NetworkStatus(true))
        val networkTracker = mock<NetworkTracker> {
            on { this.isConnected } doReturn networkFlow
        }

        val repo = mock<ImagesRepo> {
            onBlocking { this.search(any()) }
                .doReturn(Result.success(emptyList()))
        }

        val viewModel = ImageListViewModel(repo, networkTracker)
        advanceUntilIdle()
        viewModel.onChangeSearch("fruits tree")
        advanceUntilIdle()
        verify(repo, times(2)).search(any())
    }

    @Test
    fun `given search when repo returns data then state has item`() = runTest {
        val networkFlow = MutableStateFlow(NetworkStatus(true))
        val networkTracker = mock<NetworkTracker> {
            on { this.isConnected } doReturn networkFlow
        }
        val imageId = 1
        val image = Image(
            id = imageId,
            thumbnailUrl = "https://thubnail.png",
            userName = "John Smith",
            tags = emptyList()
        )

        val repo = mock<ImagesRepo> {
            onBlocking { this.search(any()) }
                .doReturn(Result.success(listOf(image)))
        }

        val viewModel = ImageListViewModel(repo, networkTracker)
        viewModel.state.test {
            assert(awaitItem() is ImagesListState.Loading)

            val state = awaitItem()
            assert(state is ImagesListState.Items)
            assertEquals((state as ImagesListState.Items).items.size, 1)
            assertEquals(state.items.first().id, 1)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
