package com.chs.youranimelist.data.domain.repository

import com.chs.youranimelist.data.domain.AnimeListDao
import com.chs.youranimelist.data.domain.model.Anime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class YourAnimeListRepositoryImpl(
    private val dao: AnimeListDao
) : YourAnimeListRepository {
    override fun getAllAnimeList(): Flow<List<Anime>> {
        return dao.getAllAnimeList()
    }

    override fun checkAnimeList(animeId: Int): Flow<Anime> {
        return dao.checkAnimeList(animeId)
    }

    override fun searchAnimeList(animeTitle: String): Flow<List<Anime>> {
        return dao.searchAnimeList(animeTitle)
    }

    override suspend fun insertAnimeList(anime: Anime) {
        dao.insertAnimeList(anime)
    }

    override suspend fun deleteAnimeList(anime: Anime) {
        dao.deleteAnimeList(anime)
    }

}