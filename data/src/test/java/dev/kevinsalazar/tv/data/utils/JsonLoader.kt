package dev.kevinsalazar.tv.data.utils

import kotlinx.serialization.json.Json

fun Any.loadFileText(
    filePath: String
): String =
    this::class.java.getResource(filePath)?.readText() ?: throw IllegalArgumentException(
        "Could not find file $filePath."
    )

val json = Json {
    ignoreUnknownKeys = true
}

inline fun <reified T> Any.loadFileJson(
    filePath: String
): T {
    return json.decodeFromString<T>(loadFileText(filePath))
}
