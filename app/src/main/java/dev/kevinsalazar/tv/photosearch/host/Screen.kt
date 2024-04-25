package dev.kevinsalazar.tv.photosearch.host

sealed class Screen(
    val route: String
) {
    data object Gallery : Screen(route = "home")
    data object Viewer : Screen(route = "viewer?url={url}") {
        fun createRoute(url: String): String {
            return "viewer?url=$url"
        }
    }
}
