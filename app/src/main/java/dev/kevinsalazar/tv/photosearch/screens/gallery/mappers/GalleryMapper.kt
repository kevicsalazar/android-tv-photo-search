package dev.kevinsalazar.tv.photosearch.screens.gallery.mappers

import dev.kevinsalazar.tv.domain.entities.Photo
import dev.kevinsalazar.tv.photosearch.screens.gallery.model.PhotoModel
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GalleryMapper @Inject constructor() {

    companion object {
        private const val TAX_TAGS = 3
    }

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
            tags = photo.tags.map { it.title }
                .take(TAX_TAGS)
        )
    }

    private fun formatDate(datetime: String): String {
        val parsedDateTime = OffsetDateTime.parse(datetime).toLocalDateTime()
        val outputFormat = DateTimeFormatter.ofPattern("MMMM d yyyy")
        return parsedDateTime.format(outputFormat)
    }

}
