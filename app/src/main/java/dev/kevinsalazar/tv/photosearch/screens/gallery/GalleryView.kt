@file:OptIn(ExperimentalMaterial3Api::class)

package dev.kevinsalazar.tv.photosearch.screens.gallery

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.foundation.lazy.grid.rememberTvLazyGridState
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Effect
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Event
import dev.kevinsalazar.tv.photosearch.ui.components.LoadingView
import dev.kevinsalazar.tv.photosearch.ui.components.PhotoItem
import dev.kevinsalazar.tv.photosearch.ui.components.SearchScreen
import dev.kevinsalazar.tv.photosearch.utils.LocalNavController
import dev.kevinsalazar.tv.photosearch.utils.getViewModel
import dev.kevinsalazar.tv.photosearch.utils.use
import kotlinx.coroutines.flow.collectLatest

const val BUFFER = 4
const val GRID_COLUMNS = 3

@Composable
fun GalleryView(
    viewModel: GalleryViewModel = getViewModel()
) {

    val (state, event, effect) = use(viewModel = viewModel)

    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        if (!state.loaded) {
            event.invoke(Event.OnLoadPhotos)
        }
    }

    LaunchedEffect(effect) {
        effect.collectLatest {
            when (it) {
                is Effect.Navigate -> navController.navigate(it.route)
                is Effect.OnBackHandler -> navController.popBackStack()
            }
        }
    }

    BackHandler {
        event.invoke(Event.OnBackHandler)
    }

    GalleryView(
        state = state,
        event = event
    )
}

@Composable
private fun GalleryView(
    state: GalleryContract.State,
    event: (Event) -> Unit
) {
    Column {
        GallerySearchView(
            state = state,
            event = event
        )
        GalleryGridView(
            state = state,
            event = event
        )
        GalleryLoadingView(
            state = state,
        )
    }
}

@Composable
private fun GallerySearchView(
    state: GalleryContract.State,
    event: (Event) -> Unit
) {

    var text by remember {
        mutableStateOf("")
    }

    if (state.searchMode) {
        SearchScreen(
            searchQuery = text,
            onSearchQueryChange = {
                text = it
            },
            onSearch = {
                event.invoke(Event.OnSearchPhotos(it))
            }
        )
    } else {
        FloatingActionButton(
            onClick = {
                event.invoke(Event.OnSearchMode)
            }
        ) {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        }
    }
}

@Composable
private fun GalleryGridView(
    state: GalleryContract.State,
    event: (Event) -> Unit
) {

    val gridState = rememberTvLazyGridState()

    val bottomReached by remember {
        derivedStateOf {
            val lastVisibleItem = gridState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == gridState.layoutInfo.totalItemsCount - BUFFER
        }
    }

    LaunchedEffect(bottomReached) {
        if (bottomReached) {
            event.invoke(Event.OnLoadMorePhotos)
        }
    }

    TvLazyVerticalGrid(
        state = gridState,
        columns = TvGridCells.Fixed(GRID_COLUMNS)
    ) {
        items(state.photos) { photo ->
            PhotoItem(
                username = photo.username,
                date = photo.date,
                tags = photo.tags,
                url = photo.thumb,
                onClick = {
                    event.invoke(Event.OnPhotoClick(photo))
                }
            )
        }
    }
}

@Composable
private fun GalleryLoadingView(
    state: GalleryContract.State,
) {
    if (state.loading) {
        LoadingView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}
