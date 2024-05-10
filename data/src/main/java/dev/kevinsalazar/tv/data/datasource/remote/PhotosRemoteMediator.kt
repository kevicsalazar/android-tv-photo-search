package dev.kevinsalazar.tv.data.datasource.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.kevinsalazar.tv.data.datasource.local.PhotoDatabase
import dev.kevinsalazar.tv.data.datasource.local.entities.PhotoEntity
import dev.kevinsalazar.tv.data.datasource.local.entities.RemoteKeyEntity
import dev.kevinsalazar.tv.data.datasource.remote.dto.PhotoDto
import dev.kevinsalazar.tv.data.mapppers.PhotoDataMapper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PhotosRemoteMediator(
    private val db: PhotoDatabase,
    private val fetcher: suspend (Int, Int) -> List<PhotoDto>
) : RemoteMediator<Int, PhotoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        db.remoteKeyDao.remoteKeyByLabel(LABEL)
                    }

                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remoteKey.nextKey
                }
            }

            val photos = fetcher.invoke(loadKey, state.config.pageSize)
            val entities = photos.map(PhotoDataMapper::mapToEntity)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeyDao.deleteByLabel(LABEL)
                    db.photoDao.clearAll()
                }

                db.remoteKeyDao.upsert(
                    RemoteKeyEntity(LABEL, loadKey + 1)
                )

                db.photoDao.upsertAll(entities)
            }

            MediatorResult.Success(
                endOfPaginationReached = photos.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val LABEL = "photos"
    }
}