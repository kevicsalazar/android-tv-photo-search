package dev.kevinsalazar.tv.photosearch.screens.viewer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kevinsalazar.tv.domain.usecases.GetLoadedPhotosUseCase
import dev.kevinsalazar.tv.photosearch.screens.gallery.mappers.GalleryMapper
import dev.kevinsalazar.tv.photosearch.screens.gallery.model.PhotoModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewerViewModel @Inject constructor(
    private val getLoadedPhotosUseCase: GetLoadedPhotosUseCase,
    private val galleryMapper: GalleryMapper,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), ViewerContract {

    private val _state = MutableStateFlow(ViewerContract.State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ViewerContract.Effect>()
    override val effect = _effect.asSharedFlow()

    val photosState = MutableStateFlow<PagingData<PhotoModel>>(PagingData.empty())

    private val id get() = checkNotNull(savedStateHandle.get<String>("id"))
    private val page get() = checkNotNull(savedStateHandle.get<Int>("page"))

    override fun event(event: ViewerContract.Event) {
        when (event) {
            ViewerContract.Event.OnLoadPhotos -> loadPhotos()
        }
    }

    private fun loadPhotos() {
        viewModelScope.launch {

            getLoadedPhotosUseCase(page)
                .map { it.map(galleryMapper::map) }
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect { data ->
                    photosState.value = data
                }
        }
    }
}
