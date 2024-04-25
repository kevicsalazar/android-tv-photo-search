@file:OptIn(ExperimentalTvMaterial3Api::class, ExperimentalTvMaterial3Api::class)

package dev.kevinsalazar.tv.photosearch.screens.gallery

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.foundation.lazy.grid.rememberTvLazyGridState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import dev.kevinsalazar.tv.photosearch.R
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Effect
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Event
import dev.kevinsalazar.tv.photosearch.ui.components.UiKitLoading
import dev.kevinsalazar.tv.photosearch.ui.components.UiKitPhotoItem
import dev.kevinsalazar.tv.photosearch.ui.components.UiKitSearchBar
import dev.kevinsalazar.tv.photosearch.utils.LocalNavController
import dev.kevinsalazar.tv.photosearch.utils.getViewModel
import dev.kevinsalazar.tv.photosearch.utils.use
import kotlinx.coroutines.flow.collectLatest

const val Buffer = 4
const val GridColumns = 3

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
    Surface(
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row {
                UiKitSearchBar(
                    active = state.searchMode,
                    onActiveChange = {
                        if (it) event.invoke(Event.OnSearchMode)
                    },
                    onSearch = {
                        event.invoke(Event.OnSearchPhotos(it))
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = stringResource(id = R.string.app_name).uppercase(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Text(
                text = state.subtitle,
                style = MaterialTheme.typography.titleMedium
            )
            GalleryGridView(
                state = state,
                event = event
            )

            UiKitLoading(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                enabled = state.loading
            )
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
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == gridState.layoutInfo.totalItemsCount - Buffer
        }
    }

    LaunchedEffect(bottomReached) {
        if (bottomReached) {
            event.invoke(Event.OnLoadMorePhotos)
        }
    }

    TvLazyVerticalGrid(
        state = gridState,
        columns = TvGridCells.Fixed(GridColumns),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(state.photos) { photo ->
            UiKitPhotoItem(
                title = photo.title,
                subtitle = photo.subtitle,
                url = photo.thumb,
                onClick = {
                    event.invoke(Event.OnPhotoClick(photo))
                }
            )
        }
    }
}
