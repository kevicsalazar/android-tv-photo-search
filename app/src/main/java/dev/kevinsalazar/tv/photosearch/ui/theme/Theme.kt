package dev.kevinsalazar.tv.photosearch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.darkColorScheme
import androidx.tv.material3.lightColorScheme

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun PhotoSearchTheme(
    isInDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isInDarkTheme) {
        darkColorScheme(
            primary = Purple80,
            primaryContainer = Cyan80,
            onPrimaryContainer = Color.White,
            secondary = PurpleGrey80,
            tertiary = Pink80,
        )
    } else {
        lightColorScheme(
            primary = Purple40,
            onPrimaryContainer = Color.White,
            secondary = PurpleGrey40,
            tertiary = Pink40,
        )
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
