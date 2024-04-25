package dev.kevinsalazar.tv.photosearch.screens.viewer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import dev.kevinsalazar.tv.photosearch.screens.viewer.ViewerContract.Event
import dev.kevinsalazar.tv.photosearch.utils.getViewModel
import dev.kevinsalazar.tv.photosearch.utils.use

@Composable
fun ViewerView(
    viewModel: ViewerViewModel = getViewModel()
) {

    val (state, event, _) = use(viewModel)

    LaunchedEffect(Unit) {
        event.invoke(
            Event.OnLoadPhoto
        )
    }

    AsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = state.url,
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
