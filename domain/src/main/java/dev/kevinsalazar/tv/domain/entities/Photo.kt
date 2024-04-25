package dev.kevinsalazar.tv.domain.entities

data class Photo(
    val id: String,
    val description: String?,
    val username: String,
    val createdAt: String,
    val urls: Urls
) {
    data class Urls(
        val raw: String,
        val thumb: String
    )
}
