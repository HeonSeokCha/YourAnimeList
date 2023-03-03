package com.chs.youranimelist.data.source.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idMal: Int,
    val title: String,
    val imageUrl: String,
    val seasonYear: Int,
    val status: String
)
