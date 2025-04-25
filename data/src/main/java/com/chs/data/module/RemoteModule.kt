package com.chs.data.module

import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.HttpFetchPolicy
import com.apollographql.apollo.cache.http.httpCache
import com.apollographql.apollo.cache.http.httpFetchPolicy
import com.apollographql.ktor.ktorClient
import com.chs.common.Constants
import com.chs.data.source.CustomHttpLogger
import com.chs.data.source.JikanService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun providerKtorHttpClient(): HttpClient {
        return HttpClient(Android) {
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

    @Singleton
    @Provides
    fun provideJikanService(
        ktorClient: HttpClient
    ): JikanService {
        return JikanService(
            ktorClient.config {
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

    @Provides
    @Singleton
    fun providesApollo(
        @ApplicationContext context: Context,
        ktorClient: HttpClient
    ): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constants.ANILIST_API_URL)
            .ktorClient(ktorClient)
            .httpCache(
                directory = context.cacheDir,
                maxSize = 100 * 1024 * 1024
            )
            .httpFetchPolicy(HttpFetchPolicy.CacheFirst)
            .build()
    }
}