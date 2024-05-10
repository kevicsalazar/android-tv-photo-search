package dev.kevinsalazar.tv.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.kevinsalazar.tv.data.datasource.local.dao.PhotoDao
import dev.kevinsalazar.tv.data.datasource.local.dao.RemoteKeyDao
import dev.kevinsalazar.tv.data.datasource.local.entities.PhotoEntity
import dev.kevinsalazar.tv.data.datasource.local.entities.RemoteKeyEntity

@Database(
    entities = [
        PhotoEntity::class,
        RemoteKeyEntity::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract val photoDao: PhotoDao
    abstract val remoteKeyDao: RemoteKeyDao
}