package dev.kevinsalazar.tv.photosearch.screens.gallery.model

data class PhotoModel(
    val id: String,
    val date: String,
    val username: String,
    val tags: List<String>,
    val thumb: String,
    val raw: String
)
