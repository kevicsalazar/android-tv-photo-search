@file:OptIn(ExperimentalTvMaterial3Api::class)

package dev.kevinsalazar.tv.photosearch.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.Text
import coil.compose.AsyncImage

private const val RATIO_16_9 = 16 / 9f

@Composable
fun PhotoItem(
    username: String,
    date: String,
    tags: List<String>,
    url: String,
    onClick: () -> Unit
) {

    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(RATIO_16_9)
            .padding(8.dp)
            .border(4.dp, Color.White.copy(alpha = if (isFocused) 1f else 0f))
            .onFocusChanged { isFocused = it.isFocused }
            .clickable {
                onClick.invoke()
            }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = url,
            contentDescription = tags.firstOrNull(),
            contentScale = ContentScale.Crop
        )
        Text(text = username)
        Text(text = date)
        Text(text = tags.joinToString(separator = " - "))
    }
}
