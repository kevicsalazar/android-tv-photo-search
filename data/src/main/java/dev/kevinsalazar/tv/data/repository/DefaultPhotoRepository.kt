package dev.kevinsalazar.tv.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.kevinsalazar.tv.data.datasource.UnsplashApi
import dev.kevinsalazar.tv.data.datasource.local.PhotoDatabase
import dev.kevinsalazar.tv.data.datasource.remote.PhotosRemoteMediator
import dev.kevinsalazar.tv.data.datasource.remote.dto.PhotoDto
import dev.kevinsalazar.tv.data.mapppers.PhotoDataMapper
import dev.kevinsalazar.tv.domain.constants.PHOTOS_PER_PAGE
import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class DefaultPhotoRepository @Inject constructor(
    private val db: PhotoDatabase,
    private val api: UnsplashApi
) : PhotoRepository {

    override suspend fun getRandomPhotos(): Flow<PagingData<Photo>> {
        return handleRequest { _, pageSize ->
            api.getRandomPhotos(pageSize)
        }
    }

    override suspend fun searchPhotos(query: String): Flow<PagingData<Photo>> {
        return handleRequest { page, pageSize ->
            api.searchPhotos(query, page, pageSize).results
        }
    }

    override suspend fun getLoadedPhotos(page: Int): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PHOTOS_PER_PAGE
            ),
            initialKey = page,
            pagingSourceFactory = {
                db.photoDao.pagingSource()
            }
        ).flow.map { it.map(PhotoDataMapper::map) }
    }

    private fun handleRequest(
        fetcher: suspend (Int, Int) -> List<PhotoDto>
    ): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PHOTOS_PER_PAGE
            ),
            remoteMediator = PhotosRemoteMediator(
                db = db,
                fetcher = fetcher
            ),
            pagingSourceFactory = {
                db.photoDao.pagingSource()
            }
        ).flow.map { it.map(PhotoDataMapper::map) }
    }
}
