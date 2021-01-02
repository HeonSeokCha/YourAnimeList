package com.chs.youranimelist.network.api

import com.chs.youranimelist.network.dto.AniList
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST


interface AnimeService {
    @POST("/")
    suspend fun getAnimeList(@Body req:RequestBody): AniList
}