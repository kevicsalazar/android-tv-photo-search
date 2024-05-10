package dev.kevinsalazar.tv.photosearch.screens.viewer

import dev.kevinsalazar.tv.photosearch.utils.UnidirectionalViewModel

interface ViewerContract :
    UnidirectionalViewModel<ViewerContract.State, ViewerContract.Event, ViewerContract.Effect> {

    data class State(
        val initialPosition: Int = 1
    )

    sealed interface Event {
        data object OnLoadPhotos : Event
    }

    sealed interface Effect
}
