package com.chs.youranimelist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.YourListDao
import com.chs.youranimelist.data.YourListDatabase

class AnimeListRepository(application: Application) {
    private val dao: YourListDao by lazy {
        YourListDatabase.getInstance(application).yourListDao()
    }

    fun getAllAnimeList(): LiveData<List<Anime>> = dao.getAllAnimeList().asLiveData()

    fun checkAnimeList(animeId: Int): LiveData<List<Anime>> =
        dao.checkAnimeList(animeId).asLiveData()

    suspend fun insertAnimeList(anime: Anime) {
        dao.insertAnimeList(anime)
    }

    suspend fun deleteAnimeList(anime: Anime) {
        dao.deleteAnimeList(anime)
    }
}