package com.chs.youranimelist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.chs.youranimelist.util.SingleLiveEvent
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.YourListDao
import com.chs.youranimelist.data.YourListDatabase
import kotlinx.coroutines.flow.collect

class AnimeListRepository(application: Application) {
    private val dao: YourListDao by lazy {
        YourListDatabase.getInstance(application).yourListDao()
    }

    fun getAllAnimeList() = dao.getAllAnimeList()

    fun checkAnimeList(animeId: Int) = dao.checkAnimeList(animeId)

    fun searchAnimeList(animeTitle: String) = dao.searchAnimeList(animeTitle)

    suspend fun insertAnimeList(anime: Anime) {
        dao.insertAnimeList(anime)
    }

    suspend fun deleteAnimeList(anime: Anime) {
        dao.deleteAnimeList(anime)
    }
}