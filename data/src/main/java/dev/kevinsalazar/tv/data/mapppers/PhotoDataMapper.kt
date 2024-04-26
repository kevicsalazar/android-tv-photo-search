package dev.kevinsalazar.tv.data.mapppers

import dev.kevinsalazar.tv.data.datasource.dto.PhotoDTO
import dev.kevinsalazar.tv.domain.entities.Photo

object PhotoDataMapper {

    fun map(photos: List<PhotoDTO>): List<Photo> {
        return photos.map(::map)
    }

    private fun map(photo: PhotoDTO): Photo {
        return Photo(
            id = photo.id,
            description = photo.description ?: photo.altDescription,
            username = photo.user.username,
            createdAt = photo.createdAt,
            urls = Photo.Urls(
                raw = photo.urls.raw,
                thumb = photo.urls.small
            )
        )
    }
}
