package dev.kevinsalazar.tv.data.repository

import dev.kevinsalazar.tv.data.datasource.UnsplashApi
import dev.kevinsalazar.tv.data.mapppers.PhotoDataMapper
import dev.kevinsalazar.tv.data.utils.handleRequest
import dev.kevinsalazar.tv.domain.constants.PHOTOS_PER_PAGE
import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.domain.errors.DataError
import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import dev.kevinsalazar.tv.domain.values.Result

class DefaultPhotoRepository(
    private val api: UnsplashApi
) : PhotoRepository {

    override suspend fun getRandomPhotos(): Result<List<Photo>, DataError> {
        return handleRequest {
            api.getRandomPhotos(PHOTOS_PER_PAGE)
                .let(PhotoDataMapper::map)
        }
    }

    override suspend fun searchPhotos(page: Int, query: String): Result<List<Photo>, DataError> {
        return handleRequest {
            api.searchPhotos(page, query, PHOTOS_PER_PAGE).results
                .let(PhotoDataMapper::map)
        }
    }
}
