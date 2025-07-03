package com.chs.data.module

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.httpCache
import com.apollographql.ktor.ktorClient
import com.chs.common.Constants
import com.chs.data.source.CustomHttpLogger
import com.chs.data.source.JikanService
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds

val provideRemoteModule = module {
    single {
        HttpClient(Android) {
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

    single {
        ApolloClient.Builder()
            .serverUrl(Constants.ANILIST_API_URL)
            .ktorClient(get())
            .httpCache(
                directory = androidContext().cacheDir,
                maxSize = 100 * 1024 * 1024
            )
            .build()
    }

    single {
        JikanService(
            get<HttpClient>().config {
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