package dev.kevinsalazar.tv.photosearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.kevinsalazar.tv.photosearch.host.HostView
import dev.kevinsalazar.tv.photosearch.ui.theme.PhotoSearchTheme

class Main : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoSearchTheme {
                HostView()
            }
        }
    }
}
