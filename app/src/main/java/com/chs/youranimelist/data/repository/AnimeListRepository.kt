package com.chs.youranimelist.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.chs.youranimelist.SingleLiveEvent
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.YourListDao
import com.chs.youranimelist.data.YourListDatabase
import kotlinx.coroutines.flow.collect

class AnimeListRepository(application: Application) {
    private val dao: YourListDao by lazy {
        YourListDatabase.getInstance(application).yourListDao()
    }

    private val _animeListResponse = SingleLiveEvent<List<Anime>>()
    val animeListResponse: LiveData<List<Anime>> get() = _animeListResponse

    suspend fun getAllAnimeList() {
        dao.getAllAnimeList().collect {
            _animeListResponse.postValue(it)
        }
    }

    fun checkAnimeList(animeId: Int): LiveData<List<Anime>> =
        dao.checkAnimeList(animeId).asLiveData()


    suspend fun insertAnimeList(anime: Anime) {
        dao.insertAnimeList(anime)
    }

    suspend fun deleteAnimeList(anime: Anime) {
        dao.deleteAnimeList(anime)
    }
}