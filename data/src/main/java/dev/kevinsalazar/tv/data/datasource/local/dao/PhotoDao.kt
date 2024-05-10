package dev.kevinsalazar.tv.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.kevinsalazar.tv.data.datasource.local.entities.PhotoEntity

@Dao
interface PhotoDao {
    @Upsert
    suspend fun upsertAll(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photos")
    fun pagingSource(): PagingSource<Int, PhotoEntity>

    @Query("DELETE FROM photos")
    suspend fun clearAll()
}