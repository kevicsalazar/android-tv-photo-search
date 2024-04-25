package dev.kevinsalazar.tv.domain.repository

import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.domain.errors.DataError
import dev.kevinsalazar.tv.domain.values.Result

interface PhotoRepository {
    suspend fun getRandomPhotos(): Result<List<Photo>, DataError>
    suspend fun searchPhotos(page: Int, query: String): Result<List<Photo>, DataError>
}