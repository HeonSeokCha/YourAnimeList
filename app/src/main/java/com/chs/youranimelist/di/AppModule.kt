package com.chs.youranimelist.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.chs.youranimelist.BuildConfig
import com.chs.youranimelist.data.YourListDatabase
import com.chs.youranimelist.data.repository.YourAnimeListRepository
import com.chs.youranimelist.data.repository.YourCharacterListRepository
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.*
import com.chs.youranimelist.network.response.AnimeDetails
import com.chs.youranimelist.network.services.JikanRestServicesImpl
import com.chs.youranimelist.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.text.get

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideYourListDatabases(app: Application): YourListDatabase {
        return Room.databaseBuilder(
            app,
            YourListDatabase::class.java,
            "yourList_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideYourAnimeListRepository(db: YourListDatabase): YourAnimeListRepository {
        return YourAnimeListRepository(db.animeListDao)
    }

    @Provides
    @Singleton
    fun provideYourCharaListRepository(db: YourListDatabase): YourCharacterListRepository {
        return YourCharacterListRepository(db.charaListDao)
    }

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
        return ApolloClient.builder()
            .serverUrl(Constant.ANILIST_API_URL)
            .okHttpClient(okHttpClient)
            .normalizedCache(
                LruNormalizedCacheFactory(
                    EvictionPolicy.builder().maxSizeBytes(10 * 1024 * 1024).build()
                )
            ).build()
    }

    @Provides
    @Singleton
    fun provideKtor(): JikanRestServicesImpl {
        return JikanRestServicesImpl(
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
        return AnimeListRepository(apollo)
    }

    @Provides
    @Singleton
    fun providesAnimeRepository(
        apollo: ApolloClient,
        jikan: JikanRestServicesImpl
    ): AnimeRepository {
        return AnimeRepository(apollo, jikan)
    }

    @Provides
    @Singleton
    fun providesCharacterRepository(apollo: ApolloClient): CharacterRepository {
        return CharacterRepository(apollo)
    }

    @Provides
    @Singleton
    fun providesSearchRepository(apollo: ApolloClient): SearchRepository {
        return SearchRepository(apollo)
    }

    @Provides
    @Singleton
    fun providesStudioRepository(apollo: ApolloClient): StudioRepository {
        return StudioRepository(apollo)
    }
}