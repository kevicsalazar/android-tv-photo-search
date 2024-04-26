package dev.kevinsalazar.tv.photosearch.screens.gallery.mappers

import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.photosearch.screens.gallery.model.PhotoModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class GalleryMapper {

    fun map(photos: List<Photo>): List<PhotoModel> {
        return photos.map(::map)
    }

    private fun map(photo: Photo): PhotoModel {
        val date = formatDate(photo.createdAt)
        return PhotoModel(
            id = photo.id,
            title = photo.description.orEmpty(),
            subtitle = "${photo.username} / $date",
            thumb = photo.urls.thumb,
            raw = photo.urls.raw,
            tags = photo.tags.map { it.title }.take(3)
        )
    }

    private fun formatDate(datetime: String): String {
        val parsedDateTime = OffsetDateTime.parse(datetime).toLocalDateTime()
        val outputFormat = DateTimeFormatter.ofPattern("MMMM d yyyy")
        return parsedDateTime.format(outputFormat)
    }

}
