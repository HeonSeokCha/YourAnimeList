package com.chs.youranimelist.network.api

import com.chs.youranimelist.network.dto.AnimeDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeDetailApi {

    companion object {
        const val BASE_URL = "https://api.jikan.moe/v3/"
    }

    @GET("anime/{animeId}")
    suspend fun getAnimeDetail (
        @Path("animeId") animeId: String
    ):AnimeDetail

}