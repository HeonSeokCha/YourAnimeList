package com.chs.youranimelist.network.api

import com.chs.youranimelist.network.dto.AnimeDetailResponse
import com.chs.youranimelist.network.dto.AnimeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeService {

    companion object {
        const val BASE_URL = "https://api.jikan.moe/v3/"
    }

    @GET("anime/{animeId}")
    suspend fun getAnimeDetail (
        @Path("animeId") animeId: String
    ): AnimeDetailResponse

    @GET("search")
    suspend fun searchAnime(
        @Query("q") query: String
    ): AnimeSearchResponse
}