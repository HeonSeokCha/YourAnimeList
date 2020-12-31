package com.chs.youranimelist.network.repository

import com.chs.youranimelist.network.api.AnimeService
import com.chs.youranimelist.network.dto.Anime
import com.chs.youranimelist.network.services.RetrofitInstance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class AnimeListRepository {
    private val api by lazy {
        RetrofitInstance.getRetrofitInstance().create(AnimeService::class.java)
    }
    val getPagerAnime: Flow<Response<Anime>> = api
        .getAnimeList()

}