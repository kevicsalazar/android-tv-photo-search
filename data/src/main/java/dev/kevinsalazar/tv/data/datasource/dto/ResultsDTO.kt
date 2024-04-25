package dev.kevinsalazar.tv.data.datasource.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResultsDTO<T>(
    val results: List<T>
)
