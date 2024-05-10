package dev.kevinsalazar.tv.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
class PhotoEntity(
    @PrimaryKey
    val id: String,
    val description: String?,
    val username: String,
    val createdAt: String,
    val url: String,
    val thumbnail: String,
    val tags: List<String>
)
