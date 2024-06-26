package dev.kevinsalazar.tv.photosearch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.kevinsalazar.tv.photosearch.R

private const val TvAspectRatio = 16 / 9f

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun UiKitPhotoItem(
    title: String,
    subtitle: String,
    url: String,
    tags: List<String>,
    onClick: () -> Unit
) {

    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(TvAspectRatio)
            .border(
                4.dp,
                MaterialTheme.colorScheme.onSurface.copy(alpha = if (isFocused) 1f else 0f)
            )
            .onFocusChanged { isFocused = it.isFocused }
            .clickable {
                onClick.invoke()
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .placeholder(R.drawable.bg_placeholder)
                .crossfade(true)
                .build(),
            contentDescription = title,
            contentScale = ContentScale.Crop
        )
        UiKitPhotoItemContent(
            title = title,
            subtitle = subtitle,
            tags = tags
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun UiKitPhotoItemContent(
    title: String,
    subtitle: String,
    tags: List<String>,
) {
    Column(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    0.0f to Color.Transparent,
                    1f to Color.Black,
                )
            )
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(tags) {
                UiKitTag(text = it)
            }
        }
    }
}
