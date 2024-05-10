package dev.kevinsalazar.tv.domain.usecases

import androidx.paging.PagingData
import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(query: String): Flow<PagingData<Photo>> {
        return repository.searchPhotos(query)
    }
}
