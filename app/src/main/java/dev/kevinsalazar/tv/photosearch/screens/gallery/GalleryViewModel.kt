package dev.kevinsalazar.tv.photosearch.screens.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.kevinsalazar.tv.domain.usecases.GetPageByPosition
import dev.kevinsalazar.tv.domain.usecases.GetPhotosUseCase
import dev.kevinsalazar.tv.domain.usecases.SearchPhotosUseCase
import dev.kevinsalazar.tv.photosearch.host.Screen
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Effect
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.Event
import dev.kevinsalazar.tv.photosearch.screens.gallery.GalleryContract.State
import dev.kevinsalazar.tv.photosearch.screens.gallery.mappers.GalleryMapper
import dev.kevinsalazar.tv.photosearch.screens.gallery.model.PhotoModel
import dev.kevinsalazar.tv.photosearch.utils.TextProvider
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase,
    private val getPageByPosition: GetPageByPosition,
    private val galleryMapper: GalleryMapper,
    private val textProvider: TextProvider
) : ViewModel(), GalleryContract {

    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    val photosState = MutableStateFlow<PagingData<PhotoModel>>(PagingData.empty())

    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    override fun event(event: Event) {
        when (event) {
            is Event.OnLoadPhotos -> loadPhotos()
            is Event.OnSearchPhotos -> searchPhotos(event.query)
            is Event.OnPhotoClick -> photoClick(event.id, event.position)
            is Event.OnSearchMode -> searchMode()
            is Event.OnBackHandler -> onBackHandler()
        }
    }

    private fun loadPhotos() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    subtitle = textProvider.trendingNow,
                    searchMode = false
                )
            }
            getPhotosUseCase()
                .map { it.map(galleryMapper::map) }
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    photosState.value = it

                }
        }
    }

    private fun searchPhotos(query: String) {
        viewModelScope.launch {
            searchPhotosUseCase(query)
                .map { it.map(galleryMapper::map) }
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .onStart {
                    _state.update { state ->
                        state.copy(
                            subtitle = textProvider.getSearchResultsFor(query),
                            searchMode = true
                        )
                    }
                }
                .collect {
                    photosState.value = it
                }
        }
    }

    private fun photoClick(id: String, position: Int) {
        viewModelScope.launch {
            val page = getPageByPosition(position)
            val route = Screen.Viewer.createRoute(id, page)
            _effect.emit(Effect.Navigate(route))
        }
    }

    private fun searchMode() {
        _state.update { state ->
            state.copy(
                searchMode = true
            )
        }
    }

    private fun onBackHandler() {
        viewModelScope.launch {
            if (state.value.searchMode) {
                loadPhotos()
            } else {
                _effect.emit(Effect.OnBackHandler)
            }
        }
    }
}
