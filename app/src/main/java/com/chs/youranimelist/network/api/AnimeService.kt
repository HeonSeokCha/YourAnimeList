package com.chs.youranimelist.network.api

import com.chs.youranimelist.network.dto.Anime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Response
import retrofit2.http.POST


interface AnimeService {
    @POST("")
    fun getAnimeList(): Flow<Response<Anime>>

}