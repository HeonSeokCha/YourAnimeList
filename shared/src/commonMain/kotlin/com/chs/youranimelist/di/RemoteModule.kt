package com.chs.youranimelist.di


import com.apollographql.apollo.ApolloClient
import com.apollographql.ktor.ktorClient
import com.chs.common.Constants
import com.chs.youranimelist.data.source.CustomHttpLogger
import com.chs.youranimelist.data.source.JikanService
import io.ktor.client.*
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import kotlin.time.Duration.Companion.seconds

@Module
class RemoteModule {
    @Single
    fun provideHttpClient(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                logger = CustomHttpLogger()
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                this.connectTimeoutMillis = 60.seconds.inWholeMilliseconds
                this.socketTimeoutMillis = 60.seconds.inWholeMilliseconds
            }
        }
    }

    @Single
    fun provideApolloClient(httpClient: HttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constants.ANILIST_API_URL)
            .ktorClient(httpClient)
            .build()
    }

    @Single
    fun provideJikanService(httpClient: HttpClient): JikanService {
        return JikanService(
            httpClient.config {
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
                defaultRequest {
                    url {
                        protocol = URLProtocol.HTTPS
                        host = Constants.JIKAN_API_URL
                    }
                    contentType(ContentType.Application.Json)
                }
            }
        )
    }
}