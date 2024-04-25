package dev.kevinsalazar.tv.photosearch.screens.viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewerViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), ViewerContract {

    private val _state = MutableStateFlow(ViewerContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ViewerContract.Effect>()
    override val effect = _effect.asSharedFlow()

    private val rawUrl: String get() = checkNotNull(savedStateHandle["url"])

    override fun event(event: ViewerContract.Event) {
        when (event) {
            ViewerContract.Event.OnLoadPhoto -> loadPhoto()
        }
    }

    private fun loadPhoto() {
        viewModelScope.launch {
            _state.update {
                it.copy(url = rawUrl)
            }
        }
    }
}
