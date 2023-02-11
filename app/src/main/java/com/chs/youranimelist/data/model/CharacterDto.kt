package com.chs.youranimelist.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterDto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val charaId: Int,
    val name: String? = null,
    val nativeName: String? = null,
    val image: String? = null,
    val favourites: Int? = null,
    val created: Long = System.currentTimeMillis(),
)