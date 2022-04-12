package com.chs.youranimelist.di

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.okHttpClient
import com.chs.youranimelist.BuildConfig
import com.chs.youranimelist.data.datasource.JikanService
import com.chs.youranimelist.data.datasource.KtorJikanService
import com.chs.youranimelist.data.repository.*
import com.chs.youranimelist.domain.repository.*
import com.chs.youranimelist.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesApollo(okHttpClient: OkHttpClient): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl(Constant.ANILIST_API_URL)
            .okHttpClient(okHttpClient)
            .normalizedCache(
                MemoryCacheFactory(
                    maxSizeBytes = 10 * 1024 * 1024
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideKtorHttpClient(): JikanService {
        return KtorJikanService(
            HttpClient(Android) {
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


    @Provides
    @Singleton
    fun providesAnimeListRepository(apollo: ApolloClient): AnimeListRepository {
        return AnimeListRepositoryImpl(apollo)
    }

    @Provides
    @Singleton
    fun providesAnimeRepository(
        apollo: ApolloClient,
        jikan: JikanService
    ): AnimeRepository {
        return AnimeRepositoryImpl(
            apollo,
            jikan
        )
    }

    @Provides
    @Singleton
    fun providesCharacterRepository(apollo: ApolloClient): CharacterRepository {
        return CharacterRepositoryImpl(apollo)
    }

    @Provides
    @Singleton
    fun providesSearchRepository(apollo: ApolloClient): SearchRepository {
        return SearchRepositoryImpl(apollo)
    }

    @Provides
    @Singleton
    fun providesStudioRepository(apollo: ApolloClient): StudioRepository {
        return StudioRepositoryImpl(apollo)
    }
}