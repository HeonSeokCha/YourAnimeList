package com.chs.youranimelist.data

import androidx.room.*
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.dto.Character
import kotlinx.coroutines.flow.Flow

@Dao
interface YourListDao {

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

    @Query("SELECT * FROM character ORDER BY id DESC")
    fun getAllCharaList(): Flow<List<Character>>

    @Query("SELECT * FROM character where charaId = :charaId LIMIT 1")
    fun checkCharaList(charaId: Int): Flow<Character>

    @Query("SELECT * FROM character WHERE name LIKE '%' || :charaName || '%' ORDER BY id DESC")
    fun searchCharaList(charaName: String): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharaList(character: Character): Long

    @Delete
    suspend fun deleteCharaList(character: Character)
}