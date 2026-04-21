package com.chs.youranimelist.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.ktor.ktorClient
import com.chs.youranimelist.data.source.CustomHttpLogger
import com.chs.youranimelist.data.source.JikanService
import com.chs.youranimelist.data.source.db.AnimeListDatabase
import com.chs.youranimelist.data.source.db.dao.AnimeListDao
import com.chs.youranimelist.data.source.db.dao.CharaListDao
import com.chs.youranimelist.data.source.db.dao.GenreDao
import com.chs.youranimelist.data.source.db.dao.SearchListDao
import com.chs.youranimelist.data.source.db.dao.TagDao
import com.chs.youranimelist.util.Constants
import io.ktor.client.HttpClient
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds

@Module
@ComponentScan("com.chs.youranimelist.data")
class DataModule {

    @Single
    fun provideHttpClient(httpClientEngine: HttpClientEngine): HttpClient {
        return HttpClient(httpClientEngine) {
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
        val cache = MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024)
        return ApolloClient.Builder()
            .serverUrl(Constants.ANILIST_API_URL)
            .ktorClient(httpClient)
            .normalizedCache(cache)
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

    @Single
    fun provideAnimeListDao(db: AnimeListDatabase): AnimeListDao = db.animeListDao

    @Single
    fun provideCharaListDao(db: AnimeListDatabase): CharaListDao = db.charaListDao

    @Single
    fun provideGenreDao(db: AnimeListDatabase): GenreDao = db.genreDao

    @Single
    fun provideSearchListDao(db: AnimeListDatabase): SearchListDao = db.searchListDao

    @Single
    fun provideTagDao(db: AnimeListDatabase): TagDao = db.tagDao
}