package com.chs.youranimelist.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "anime")
@Parcelize
data class Anime(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val animeId: Int = 0,
    val idMal: Int = 0,
    val title: String = "",
    val format: String = "",
    val status: String? = null,
    val season: String? = null,
    val seasonYear: Int? = null,
    val episode: Int? = null,
    val coverImage: String? = null,
    val bannerImage: String? = null,
    val averageScore: Int? = null,
    val favorites: Int? = null,
    val studio: String? = null,
    val genre: List<String?>? = null,
    val created: Long = System.currentTimeMillis(),
) : Parcelable