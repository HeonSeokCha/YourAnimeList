package com.chs.youranimelist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.chs.youranimelist.util.SingleLiveEvent
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.YourListDao
import com.chs.youranimelist.data.YourListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

class AnimeListRepository(application: Application) {
    private val dao: YourListDao by lazy {
        YourListDatabase.getInstance(application).yourListDao()
    }

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