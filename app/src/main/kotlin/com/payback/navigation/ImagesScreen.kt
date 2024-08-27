package com.payback.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.payback.ui.images.details.ImageDetailsScreen
import com.payback.ui.images.details.ImageDetailsViewModel
import com.payback.ui.images.list.ImageListScreen
import com.payback.ui.images.list.ImageListViewModel

private const val PARAM_IMAGE_ID = "image_id"

internal enum class ImagesScreen {
    List,
    Details
}

internal fun ImagesScreen.toPath(): String {
    return "users/" + when (this) {
        ImagesScreen.List -> "list"
        ImagesScreen.Details -> "details"
    }
}

internal fun ImagesScreen.toRoute(): String {
    return this.toPath() + when (this) {
        ImagesScreen.List -> ""
        ImagesScreen.Details -> "?$PARAM_IMAGE_ID={$PARAM_IMAGE_ID}"
    }
}

internal fun NavGraphBuilder.imagesNavGraph(navController: NavController) {
    composable(route = ImagesScreen.List.toRoute()) {
        val viewModel = hiltViewModel<ImageListViewModel>()

        ImageListScreen(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            viewModel = viewModel,
            onNavigateDetails = { imageId ->
                val target = ImagesScreen.Details.toPath() + "?$PARAM_IMAGE_ID=$imageId"
                navController.navigate(target)
            }
        )
    }

    composable(
        route = ImagesScreen.Details.toRoute(),
        arguments = listOf(navArgument(PARAM_IMAGE_ID) { type = NavType.IntType }),
    ) { backStack ->
        val imageId = backStack.arguments?.getInt(PARAM_IMAGE_ID)
        requireNotNull(imageId) { "nav param $PARAM_IMAGE_ID not found" }

        val viewModel = hiltViewModel(
            key = ImageDetailsViewModel::class.java.name + "#$imageId",
            creationCallback = { factory: ImageDetailsViewModel.Factory ->
                factory.create(imageId)
            }
        )

        ImageDetailsScreen(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            viewModel = viewModel,
            onClickBack = navController::popBackStack
        )
    }
}
