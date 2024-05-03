package dev.kevinsalazar.tv.domain.usecases

import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.domain.errors.Error
import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import dev.kevinsalazar.tv.domain.values.Result
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {

    suspend operator fun invoke(): Result<List<Photo>, Error> {
        return photoRepository.getRandomPhotos()
    }

}
