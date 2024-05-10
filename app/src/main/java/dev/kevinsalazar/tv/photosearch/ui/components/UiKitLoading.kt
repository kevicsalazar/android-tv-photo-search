package dev.kevinsalazar.tv.photosearch.ui.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun UiKitLoading(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface
    )
}
