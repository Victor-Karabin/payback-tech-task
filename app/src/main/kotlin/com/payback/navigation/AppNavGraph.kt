package com.payback.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
internal fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ImagesScreen.List.toPath()
    ) {
        this.imagesNavGraph(navController)
    }
}