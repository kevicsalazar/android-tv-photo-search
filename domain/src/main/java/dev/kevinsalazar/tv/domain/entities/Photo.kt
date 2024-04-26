package dev.kevinsalazar.tv.domain.entities

data class Photo(
    val id: String,
    val description: String?,
    val username: String,
    val createdAt: String,
    val urls: Urls,
    val tags: List<Tag>
) {
    data class Urls(
        val raw: String,
        val thumb: String
    )
    data class Tag(
        val title: String
    )
}
