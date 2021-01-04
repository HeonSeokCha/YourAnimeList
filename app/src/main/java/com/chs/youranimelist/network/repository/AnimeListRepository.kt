package com.chs.youranimelist.network.repository

import com.chs.youranimelist.network.api.AnimeService
import com.chs.youranimelist.network.dto.AniList
import com.chs.youranimelist.network.dto.Data
import com.chs.youranimelist.network.services.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call

class AnimeListRepository {
    private val api by lazy {
        RetrofitInstance.getRetrofitInstance().create(AnimeService::class.java)
    }

    suspend fun getAnimeList(req: RequestBody): Flow<AniList> {
        return flow {
            emit(api.getAnimeList(req))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAnimeInfo(req: RequestBody): Flow<AniList> {
        return flow {
            emit(api.getAnimeInfo(req))
        }.flowOn(Dispatchers.IO)
    }
}