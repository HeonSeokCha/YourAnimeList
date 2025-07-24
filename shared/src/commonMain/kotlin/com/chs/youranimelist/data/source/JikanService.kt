package com.chs.youranimelist.data.source

import com.chs.youranimelist.data.model.JikanAnimeDataDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.*

class JikanService(private val client: HttpClient) {
    suspend fun getAnimeTheme(malId: Int): JikanAnimeDataDto {
        return client.get("$malId/themes").body()
    }
}

class CustomHttpLogger : Logger {
    override fun log(message: String) {
    }
}