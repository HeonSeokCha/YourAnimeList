package com.chs.youranimelist.data.source.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Entity(tableName = "animeInfo")
data class AnimeEntity @OptIn(ExperimentalTime::class) constructor(
    @PrimaryKey
    val id: Int,
    val idMal: Int,
    val title: String,
    val imageUrl: String?,
    val imagePlaceColor: String?,
    val averageScore: Int,
    val season: String,
    val seasonYear: Int,
    val favourites: Int,
    val format: String,
    val status: String,
    val createDate: Long = Clock.System.now().toEpochMilliseconds()
)