package dev.kevinsalazar.tv.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val id: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int,
    val description: String? = null,
    @SerialName("alt_description")
    val altDescription: String?,
    val urls: Urls,
    val user: User,
    val tags: List<Tag>? = null
) {

    @Serializable
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
    )

    @Serializable
    data class User(
        val id: String,
        val username: String,
        val name: String,
    )

    @Serializable
    data class Tag(
        val title: String
    )
}
