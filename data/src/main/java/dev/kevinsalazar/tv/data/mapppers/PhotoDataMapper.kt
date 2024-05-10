package dev.kevinsalazar.tv.data.mapppers

import dev.kevinsalazar.tv.data.datasource.local.entities.PhotoEntity
import dev.kevinsalazar.tv.data.datasource.remote.dto.PhotoDto
import dev.kevinsalazar.tv.domain.entities.Photo

object PhotoDataMapper {

    fun mapToEntity(photoDto: PhotoDto): PhotoEntity {
        return PhotoEntity(
            id = photoDto.id,
            description = photoDto.description,
            username = photoDto.user.username,
            createdAt = photoDto.createdAt,
            url = photoDto.urls.raw,
            thumbnail = photoDto.urls.regular,
            tags = photoDto.tags?.map { it.title } ?: emptyList()
        )
    }

    fun map(entity: PhotoEntity): Photo {
        return Photo(
            id = entity.id,
            description = entity.description,
            username = entity.username,
            createdAt = entity.createdAt,
            url = entity.url,
            thumbnail = entity.thumbnail,
            tags = entity.tags
        )
    }
}
