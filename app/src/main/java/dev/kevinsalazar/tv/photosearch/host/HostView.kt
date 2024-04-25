@file:OptIn(ExperimentalTvMaterial3Api::class)

package dev.kevinsalazar.tv.photosearch.host

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.tv.material3.ExperimentalTvMaterial3Api
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryView
import dev.kevinsalazar.tv.photosearch.screens.viewer.ViewerView
import dev.kevinsalazar.tv.photosearch.utils.NavControllerProvider


@Composable
fun HostView() {

    val navController = rememberNavController()

    NavControllerProvider(
        navController = navController
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = Screen.Gallery.route
        ) {
            composable(Screen.Gallery.route) { GalleryView() }
            composable(
                Screen.Viewer.route,
                arguments = listOf(
                    navArgument("url") {
                        type = NavType.StringType
                        nullable = true
                    }
                )
            ) { ViewerView() }
        }
    }
}
