package dev.kevinsalazar.tv.photosearch.host

sealed class Screen(
    val route: String
) {
    data object Gallery : Screen(route = "home")
    data object Viewer : Screen(route = "viewer?id={id}&page={page}") {
        fun createRoute(id: String, page: Int): String {
            return "viewer?id=$id&page=$page"
        }
    }
}
