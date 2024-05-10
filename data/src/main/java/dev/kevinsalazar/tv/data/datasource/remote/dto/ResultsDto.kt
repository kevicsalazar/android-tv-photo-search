package dev.kevinsalazar.tv.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultsDto<T>(
    @SerialName("total_pages")
    val totalPages: Int,
    val results: List<T>,
)
