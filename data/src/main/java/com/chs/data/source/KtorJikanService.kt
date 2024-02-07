package com.chs.data.source

import com.chs.common.Constants
import com.chs.data.model.JikanAnimeDataDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorJikanService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getAnimeTheme(malId: Int): JikanAnimeDataDto {
        return client.get("${Constants.JIKAN_API_URL}/$malId/themes") {
            headers.append("Content-Type", "application/json")
        }.body()
    }
}