package dev.kevinsalazar.tv.photosearch.screens.viewer

import dev.kevinsalazar.tv.photosearch.utils.UnidirectionalViewModel

interface ViewerContract :
    UnidirectionalViewModel<ViewerContract.State, ViewerContract.Event, ViewerContract.Effect> {

    data class State(
        val url: String = ""
    )

    sealed interface Event {
        data object OnLoadPhoto : Event
    }

    sealed interface Effect
}
