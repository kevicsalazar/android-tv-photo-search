package dev.kevinsalazar.tv.domain.usecases

import androidx.paging.PagingData
import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {

    suspend operator fun invoke(): Flow<PagingData<Photo>> {
        return photoRepository.getRandomPhotos()
    }
}
