package com.chs.youranimelist.data.source

import androidx.room.*
import com.chs.youranimelist.data.model.AnimeDto
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeListDao {

    @Query("SELECT * FROM anime ORDER BY id DESC")
    fun getAllAnimeList(): Flow<List<AnimeDto>>

    @Query("SELECT * FROM anime WHERE animeId = :animeId LIMIT 1")
    fun checkAnimeList(animeId: Int): Flow<AnimeDto>

    @Query("SELECT * FROM anime WHERE title LIKE '%' || :animeTitle || '%' ORDER BY id DESC")
    fun searchAnimeList(animeTitle: String): Flow<List<AnimeDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeList(anime: AnimeDto): Long

    @Delete
    suspend fun deleteAnimeList(anime: AnimeDto)
}