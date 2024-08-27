package com.payback.ui.images.list

import com.payback.common.MainCoroutineRule
import com.payback.ui.images.list.models.ImagesListState
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class ImageListViewModelInitTests {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val viewModel by lazy {
        ImageListViewModel(mock(), mock())
    }

    @Test
    fun `given viewmodel when initial state then 'fruits' is default search`() {
        assertEquals("fruits", viewModel.search.value)
    }

    @Test
    fun `given viewmodel when initial state then loading is default state`() {
        assertEquals(ImagesListState.Loading, viewModel.state.value)
    }
}
