package com.chs.youranimelist.data.repository

import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.YourListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class AnimeListRepository(private val dao: YourListDao) {

    fun getAllAnimeList() = dao.getAllAnimeList().flowOn(Dispatchers.IO)

    fun checkAnimeList(animeId: Int) = dao.checkAnimeList(animeId).flowOn(Dispatchers.IO)

    fun searchAnimeList(animeTitle: String) = dao.searchAnimeList(animeTitle).flowOn(Dispatchers.IO)

    suspend fun insertAnimeList(anime: Anime) {
        dao.insertAnimeList(anime)
    }

    suspend fun deleteAnimeList(anime: Anime) {
        dao.deleteAnimeList(anime)
    }
}