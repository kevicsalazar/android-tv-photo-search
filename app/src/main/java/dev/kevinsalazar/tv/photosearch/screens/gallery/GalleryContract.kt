package dev.kevinsalazar.tv.photosearch.screens.gallery

import androidx.compose.runtime.Stable
import dev.kevinsalazar.tv.photosearch.utils.UnidirectionalViewModel

interface GalleryContract :
    UnidirectionalViewModel<GalleryContract.State, GalleryContract.Event, GalleryContract.Effect> {

    @Stable
    data class State(
        val subtitle: String = "",
        val searchMode: Boolean = false
    )

    sealed interface Event {
        data object OnLoadPhotos : Event
        data class OnSearchPhotos(val query: String) : Event
        data class OnPhotoClick(val id: String, val position: Int) : Event
        data object OnSearchMode : Event
        data object OnBackHandler : Event
    }

    sealed interface Effect {
        data class Navigate(val route: String) : Effect
        data object OnBackHandler : Effect
    }
}
