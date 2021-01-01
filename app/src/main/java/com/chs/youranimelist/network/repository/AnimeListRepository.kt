package com.chs.youranimelist.network.repository

import com.chs.youranimelist.network.api.AnimeService
import com.chs.youranimelist.network.dto.Anime
import com.chs.youranimelist.network.services.RetrofitInstance
import org.json.JSONObject
import retrofit2.Call

class AnimeListRepository(private val req: JSONObject) {
    private val api by lazy {
        RetrofitInstance.getRetrofitInstance().create(AnimeService::class.java)
    }

    suspend fun getAnimeList() = api.getAnimeListAsync(req)

}