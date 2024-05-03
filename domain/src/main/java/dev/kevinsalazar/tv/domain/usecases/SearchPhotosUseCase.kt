package dev.kevinsalazar.tv.domain.usecases

import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.domain.errors.Error
import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import dev.kevinsalazar.tv.domain.values.Result
import javax.inject.Inject

class SearchPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {

    suspend operator fun invoke(page: Int, query: String): Result<List<Photo>, Error> {
        return repository.searchPhotos(page, query)
    }

}
