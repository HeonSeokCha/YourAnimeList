package com.chs.youranimelist.di

import android.app.Application
import androidx.room.Room
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory
import com.chs.youranimelist.BuildConfig
import com.chs.youranimelist.data.domain.YourListDatabase
import com.chs.youranimelist.data.domain.repository.YourAnimeListRepository
import com.chs.youranimelist.data.domain.repository.YourAnimeListRepositoryImpl
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepository
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepositoryImpl
import com.chs.youranimelist.data.remote.repository.*
import com.chs.youranimelist.data.remote.services.JikanService
import com.chs.youranimelist.data.remote.services.KtorJikanService
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
        return YourAnimeListRepositoryImpl(db.animeListDao)
    }

    @Provides
    @Singleton
    fun provideYourCharaListRepository(db: YourListDatabase): YourCharacterListRepository {
        return YourCharacterListRepositoryImpl(db.charaListDao)
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
        return AnimeListRepository(apollo)
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