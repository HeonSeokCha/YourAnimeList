package com.chs.youranimelist.network.services

import com.chs.youranimelist.network.response.AnimeDetails
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface JikanRestService {

    suspend fun getAnimeTheme(malId: Int): AnimeDetails?

    companion object {
        fun create(): JikanRestService {
            return JikanRestServicesImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                            this.ignoreUnknownKeys = true
                        })
                    }
                }
            )
        }
    }
}