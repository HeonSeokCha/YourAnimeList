package com.chs.youranimelist.data.datasource

import com.chs.youranimelist.data.model.AnimeDetails
import com.chs.youranimelist.util.Constant
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Singleton

@Singleton
class KtorJikanService(
    private val client: HttpClient
) : JikanService {
    override suspend fun getAnimeTheme(malId: Int): AnimeDetails? {
        return client.get("${Constant.JIKAN_API_URL}/$malId").body()
    }
}