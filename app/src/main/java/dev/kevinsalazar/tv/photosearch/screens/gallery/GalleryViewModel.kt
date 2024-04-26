package dev.kevinsalazar.tv.photosearch.screens.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.domain.errors.Error
import dev.kevinsalazar.tv.domain.usecases.GetPhotosUseCase
import dev.kevinsalazar.tv.domain.usecases.SearchPhotosUseCase
import dev.kevinsalazar.tv.domain.values.Result
import dev.kevinsalazar.tv.domain.values.onFailure
import dev.kevinsalazar.tv.domain.values.onSuccess
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GalleryViewModel(
    private val getPhotosUseCase: GetPhotosUseCase,
    private val searchPhotosUseCase: SearchPhotosUseCase,
    private val galleryMapper: GalleryMapper,
    private val textProvider: TextProvider
) : ViewModel(), GalleryContract {

    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    private var currentQuery = ""
    private var searchPage = 1

    override fun event(event: Event) {
        when (event) {
            is Event.OnLoadPhotos -> loadPhotos(false)
            is Event.OnSearchPhotos -> searchPhotos(event.query, false)
            is Event.OnLoadMorePhotos -> loadMorePhotos()
            is Event.OnPhotoClick -> photoClick(event.photo)
            is Event.OnSearchMode -> searchMode()
            is Event.OnBackHandler -> onBackHandler()
        }
    }

    private fun loadPhotos(loadMore: Boolean) {
        viewModelScope.launch {
            handleResult(
                searchMode = false,
                loadMore = loadMore,
                subtitle = textProvider.trendingNow,
                emptyMessage = textProvider.noResults
            ) { getPhotosUseCase() }
        }
    }

    private fun searchPhotos(query: String, loadMore: Boolean) {
        currentQuery = query
        viewModelScope.launch {
            handleResult(
                searchMode = true,
                loadMore = loadMore,
                subtitle = textProvider.getSearchResultsFor(currentQuery),
                emptyMessage = textProvider.getNoResultsFor(currentQuery)
            ) { searchPhotosUseCase(searchPage, currentQuery) }
        }
    }

    private fun loadMorePhotos() {
        if (state.value.searchMode) {
            searchPage++
            searchPhotos(currentQuery, true)
        } else {
            loadPhotos(true)
        }
    }

    private fun photoClick(photo: PhotoModel) {
        viewModelScope.launch {
            val route = Screen.Viewer.createRoute(photo.raw)
            _effect.emit(Effect.Navigate(route))
        }
    }

    private fun searchMode() {
        _state.update {
            it.copy(
                searchMode = true
            )
        }
    }

    private fun onBackHandler() {
        viewModelScope.launch {
            if (state.value.searchMode) {
                currentQuery = ""
                searchPage = 1
                loadPhotos(false)
            } else {
                _effect.emit(Effect.OnBackHandler)
            }
        }
    }

    private suspend fun handleResult(
        searchMode: Boolean,
        loadMore: Boolean,
        subtitle: String,
        emptyMessage: String,
        block: suspend () -> Result<List<Photo>, Error>
    ) {
        _state.update {
            if (loadMore) {
                it.copy(
                    loading = true,
                    searchMode = searchMode
                )
            } else {
                it.copy(
                    loading = false,
                    photos = emptyList(),
                    searchMode = searchMode
                )
            }
        }
        block.invoke()
            .onSuccess { photos ->
                _state.update {
                    val incomingPhotos = galleryMapper.map(photos)
                    val newPhotos = if (loadMore) it.photos + incomingPhotos else incomingPhotos
                    it.copy(
                        initialized = true,
                        subtitle = if (newPhotos.isEmpty()) emptyMessage else subtitle,
                        photos = newPhotos,
                        loading = false
                    )
                }
            }
            .onFailure { error, _ ->
                _state.update {
                    it.copy(
                        loading = false,
                        subtitle = textProvider.getErrorMessage(error)
                    )
                }
            }
    }
}
