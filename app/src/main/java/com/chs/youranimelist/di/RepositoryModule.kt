package com.chs.youranimelist.di

import com.apollographql.apollo3.ApolloClient
import com.chs.youranimelist.data.repository.AnimeDetailRepositoryImpl
import com.chs.youranimelist.data.repository.AnimeListRepositoryImpl
import com.chs.youranimelist.data.source.AnimeListDatabase
import com.chs.youranimelist.data.source.KtorJikanService
import com.chs.youranimelist.domain.repository.AnimeDetailRepository
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.domain.usecase.AddYourAnime
import com.chs.youranimelist.domain.usecase.AnimeUseCases
import com.chs.youranimelist.domain.usecase.DeleteYourAnime
import com.chs.youranimelist.domain.usecase.GetYourAnimes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAnimeRepository(
        client: ApolloClient,
        db: AnimeListDatabase
    ): AnimeListRepository {
        return AnimeListRepositoryImpl(client, db.animeListDao)
    }

    @Provides
    @Singleton
    fun provideAnimeDetailRepository(
        client: ApolloClient,
        jikanService: KtorJikanService
    ): AnimeDetailRepository {
        return AnimeDetailRepositoryImpl(client, jikanService)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: AnimeListRepository): AnimeUseCases {
        return AnimeUseCases(
            getAnime = GetYourAnimes(repository),
            deleteAnime = DeleteYourAnime(repository),
            addYourAnime = AddYourAnime(repository),
        )
    }
}