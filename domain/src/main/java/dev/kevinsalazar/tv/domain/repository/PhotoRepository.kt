package dev.kevinsalazar.tv.domain.repository

import androidx.paging.PagingData
import dev.kevinsalazar.tv.domain.entities.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    suspend fun getRandomPhotos(): Flow<PagingData<Photo>>
    suspend fun searchPhotos(query: String): Flow<PagingData<Photo>>
    suspend fun getLoadedPhotos(page: Int): Flow<PagingData<Photo>>
}
