package dev.kevinsalazar.tv.domain.entities

data class Photo(
    val id: String,
    val description: String?,
    val username: String,
    val createdAt: String,
    val url: String,
    val thumbnail: String,
    val tags: List<String>
)
