package dev.kevinsalazar.tv.photosearch.screens.gallery.mappers

import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.photosearch.screens.gallery.model.PhotoModel

class GalleryMapper {

    fun map(photos: List<Photo>): List<PhotoModel> {
        return photos.map(::map)
    }

    private fun map(photo: Photo): PhotoModel {
        return PhotoModel(
            id = photo.id,
            date = "",
            username = "",
            tags = listOf(),
            thumb = photo.urls.thumb,
            raw = photo.urls.raw,
        )
    }
}
