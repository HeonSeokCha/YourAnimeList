package com.chs.youranimelist.data.source.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characterInfo")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val nativeName: String,
    val imageUrl: String,
    val favorite: Int,
    val createDate: Long = System.currentTimeMillis()
)
