package com.chs.youranimelist.network.api

import com.chs.youranimelist.network.dto.Anime
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.POST


interface AnimeService {
    @POST("")
    suspend fun getAnimeListAsync(@Body req:JSONObject): List<Anime>
}