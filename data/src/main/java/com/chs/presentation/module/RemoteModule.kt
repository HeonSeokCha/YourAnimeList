package com.chs.presentation.module

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.chs.presentation.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Singleton
    @Provides
    fun providerKtorHttpClient(): com.chs.presentation.source.KtorJikanService {
        return com.chs.presentation.source.KtorJikanService(
            HttpClient(Android) {
                install(Logging) {
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(Json { ignoreUnknownKeys = true })
                }
            }
        )
    }

    @Provides
    @Singleton
    fun providesApollo(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constant.ANILIST_API_URL)
            .addInterceptor(LoggingApolloInterceptor())
            .normalizedCache(
                MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
            ).build()
    }
}