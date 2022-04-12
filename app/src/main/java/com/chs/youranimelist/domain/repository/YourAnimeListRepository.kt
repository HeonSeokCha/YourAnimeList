package com.chs.youranimelist.domain.repository

import com.chs.youranimelist.data.model.Anime
import kotlinx.coroutines.flow.Flow

interface YourAnimeListRepository {

    fun getAllAnimeList(): Flow<List<Anime>>

    fun checkAnimeList(animeId: Int): Flow<Anime?>

    fun searchAnimeList(animeTitle: String): Flow<List<Anime>>

    suspend fun insertAnimeList(anime: Anime)

    suspend fun deleteAnimeList(anime: Anime)
}