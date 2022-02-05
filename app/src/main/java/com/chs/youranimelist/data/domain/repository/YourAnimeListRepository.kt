package com.chs.youranimelist.data.domain.repository

import com.chs.youranimelist.data.domain.model.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

interface YourAnimeListRepository {

    fun getAllAnimeList(): Flow<List<Anime>>

    fun checkAnimeList(animeId: Int): Flow<Anime>

    fun searchAnimeList(animeTitle: String): Flow<List<Anime>>

    suspend fun insertAnimeList(anime: Anime)

    suspend fun deleteAnimeList(anime: Anime)
}