package com.chs.youranimelist.util

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun listToJson(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun jsonToList(value: String) = Json.decodeFromString<List<String>>(value)
}