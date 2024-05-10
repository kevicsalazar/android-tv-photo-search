@file:OptIn(ExperimentalTvMaterial3Api::class)

package dev.kevinsalazar.tv.photosearch.screens.gallery

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import dev.kevinsalazar.tv.photosearch.R
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Effect
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Event
import dev.kevinsalazar.tv.photosearch.screens.gallery.model.PhotoModel
import dev.kevinsalazar.tv.photosearch.ui.components.UiKitLoading
import dev.kevinsalazar.tv.photosearch.ui.components.UiKitPhotoItem
import dev.kevinsalazar.tv.photosearch.ui.components.UiKitSearchBar
import dev.kevinsalazar.tv.photosearch.utils.LocalNavController
import dev.kevinsalazar.tv.photosearch.utils.use
import kotlinx.coroutines.flow.collectLatest

const val GridColumns = 3

@Composable
fun GalleryView(
    viewModel: GalleryViewModel = hiltViewModel()
) {

    val (state, event, effect) = use(viewModel = viewModel)
    val photos = viewModel.photosState.collectAsLazyPagingItems()

    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        event.invoke(Event.OnLoadPhotos)
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
        event = event,
        photos = photos
    )
}

@OptIn(
    ExperimentalTvMaterial3Api::class,
    ExperimentalTvMaterial3Api::class
)
@Composable
private fun GalleryView(
    state: GalleryContract.State,
    event: (Event) -> Unit,
    photos: LazyPagingItems<PhotoModel>
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
                photos = photos,
                event = event
            )
        }
    }
}

@Composable
private fun GalleryGridView(
    photos: LazyPagingItems<PhotoModel>,
    event: (Event) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(photos.loadState) {
        if (photos.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (photos.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (photos.loadState.refresh is LoadState.Loading) {
            UiKitLoading(
                modifier = Modifier
                    .align(Alignment.Center)
            )
        } else {
            TvLazyVerticalGrid(
                columns = TvGridCells.Fixed(GridColumns),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = photos.itemCount,
                    key = photos.itemKey { it.id }
                ) { index ->
                    val item = photos[index]
                    if (item != null) {
                        UiKitPhotoItem(
                            title = item.title,
                            subtitle = item.subtitle,
                            url = item.thumb,
                            tags = item.tags,
                            onClick = {
                                event.invoke(Event.OnPhotoClick(item.id, index))
                            }
                        )
                    }
                }
                item {
                    if (photos.loadState.append is LoadState.Loading) {
                        UiKitLoading(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
