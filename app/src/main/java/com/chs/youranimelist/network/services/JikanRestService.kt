package com.chs.youranimelist.network.services

import com.chs.youranimelist.network.response.AnimeDetails
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface JikanRestService {
    suspend fun getAnimeTheme(malId: Int): AnimeDetails?
}