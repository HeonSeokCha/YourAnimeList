package com.chs.youranimelist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface YourListDao {

    @Query("SELECT * FROM anime ORDER BY id DESC")
    fun getAllAnimeList(): Flow<List<Anime>>

    @Query("SELECT * FROM anime where animeId = :animeId")
    fun checkAnimeList(animeId: Int): Flow<List<Anime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeList(anime: Anime): Long

    @Delete
    suspend fun deleteAnimeList(anime: Anime)

    @Query("SELECT * FROM character ORDER BY id DESC")
    fun getAllCharaList(): Flow<List<Character>>

    @Query("SELECT * FROM character where charaId = :charaId")
    fun checkCharaList(charaId: Int): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharaList(character: Character): Long

    @Delete
    suspend fun deleteCharaList(character: Character)
}