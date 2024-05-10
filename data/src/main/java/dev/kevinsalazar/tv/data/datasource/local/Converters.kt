package dev.kevinsalazar.tv.data.datasource.local

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Converters {

    @TypeConverter
    @JvmStatic
    fun fromListToString(list: List<String>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    @JvmStatic
    fun fromStringToList(value: String?): List<String> {
        return value?.let { Json.decodeFromString(value) } ?: emptyList()
    }

}