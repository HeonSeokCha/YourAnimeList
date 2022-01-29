package com.chs.youranimelist.data

import androidx.room.*
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.dto.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeListDao {

    @Query("SELECT * FROM anime ORDER BY id DESC")
    fun getAllAnimeList(): Flow<List<Anime>>

    @Query("SELECT * FROM anime WHERE animeId = :animeId LIMIT 1")
    fun checkAnimeList(animeId: Int): Flow<Anime>

    @Query("SELECT * FROM anime WHERE title LIKE '%' || :animeTitle || '%' ORDER BY id DESC")
    fun searchAnimeList(animeTitle: String): Flow<List<Anime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeList(anime: Anime): Long

    @Delete
    suspend fun deleteAnimeList(anime: Anime)
}