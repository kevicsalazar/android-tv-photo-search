package dev.kevinsalazar.tv.photosearch.screens.viewer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Surface
import coil.compose.SubcomposeAsyncImage
import dev.kevinsalazar.tv.photosearch.screens.viewer.ViewerContract.Event
import dev.kevinsalazar.tv.photosearch.ui.components.UiKitLoading
import dev.kevinsalazar.tv.photosearch.utils.use

@OptIn(
    ExperimentalTvMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun ViewerView(
    viewModel: ViewerViewModel = hiltViewModel()
) {

    val (state, event, _) = use(viewModel)
    val photos = viewModel.photosState.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(pageCount = { photos.itemCount })

    LaunchedEffect(Unit) {
        event.invoke(
            Event.OnLoadPhotos
        )
    }

    Surface(
        shape = RectangleShape
    ) {
        HorizontalPager(
            state = pagerState,
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = photos[it]?.raw,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        UiKitLoading()
                    }
                }
            )
        }
    }
}
