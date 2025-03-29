package com.chs.data.source

import com.chs.data.model.JikanAnimeDataDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JikanService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getAnimeTheme(malId: Int): JikanAnimeDataDto {
        return client.get("$malId/themes").body()
    }
}