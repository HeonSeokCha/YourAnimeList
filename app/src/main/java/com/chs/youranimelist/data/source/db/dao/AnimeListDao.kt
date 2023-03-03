package com.chs.youranimelist.data.source.db.dao

import androidx.room.*
import com.chs.youranimelist.data.model.AnimeDto
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AnimeListDao : BaseDao<AnimeDto> {

    @Query("SELECT * FROM anime ORDER BY id DESC")
    abstract fun getAllAnimeList(): Flow<List<AnimeDto>>

    @Query("SELECT * FROM anime WHERE animeId = :animeId LIMIT 1")
    abstract fun checkAnimeList(animeId: Int): Flow<AnimeDto>

    @Query("SELECT * FROM anime WHERE title LIKE '%' || :animeTitle || '%' ORDER BY id DESC")
    abstract fun searchAnimeList(animeTitle: String): Flow<List<AnimeDto>>
}