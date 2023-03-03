package com.chs.youranimelist.data.source.db.model

import androidx.room.PrimaryKey

data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val nativeName: String,
    val imageUrl: String,
    val favorite: Int
)
