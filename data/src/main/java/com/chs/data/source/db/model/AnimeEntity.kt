package com.chs.data.source.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animeInfo")
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idMal: Int,
    val title: String,
    val imageUrl: String?,
    val imagePlaceColor: String?,
    val averageScore: Int,
    val seasonYear: Int,
    val favourites: Int,
    val format: String,
    val status: String,
    val createDate: Long = System.currentTimeMillis()
)