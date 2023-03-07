package com.chs.youranimelist.source

import com.chs.youranimelist.model.JikanAnimeDataDto
import com.chs.youranimelist.util.Constant
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorJikanService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getAnimeTheme(malId: Int): JikanAnimeDataDto? {
        return client.get("${Constant.JIKAN_API_URL}/$malId/themes") {
            headers.append("Content-Type", "application/json")
        }.body()
    }
}