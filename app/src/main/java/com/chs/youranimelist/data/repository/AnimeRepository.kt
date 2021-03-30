package com.chs.youranimelist.data.repository

import android.app.Application
import com.chs.youranimelist.data.Anime
import com.chs.youranimelist.data.YourListDao
import com.chs.youranimelist.data.YourListDatabase
import kotlinx.coroutines.flow.Flow

class AnimeRepository(application: Application) {
    private val dao: YourListDao by lazy {
        val db = YourListDatabase.getInstance(application)
        db.yourListDao()
    }

    fun getAllAnimeList(): Flow<List<Anime>> = dao.getAllAnimeList()

    suspend fun insertAnimeList(anime: Anime) {
        dao.insertAnimeList(anime)
    }

    suspend fun deleteAnimeList(anime: Anime) {
        dao.deleteAnimeList(anime)
    }
}