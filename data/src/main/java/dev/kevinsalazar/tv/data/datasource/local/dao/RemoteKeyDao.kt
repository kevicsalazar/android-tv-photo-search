package dev.kevinsalazar.tv.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.kevinsalazar.tv.data.datasource.local.entities.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Upsert
    suspend fun upsert(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE label = :label")
    suspend fun remoteKeyByLabel(label: String): RemoteKeyEntity

    @Query("DELETE FROM remote_keys WHERE label = :label")
    suspend fun deleteByLabel(label: String)
}