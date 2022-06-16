package com.chs.youranimelist.di

import com.chs.youranimelist.data.repository.AnimeListRepositoryImpl
import com.chs.youranimelist.data.source.AnimeListDatabase
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.domain.usecase.AddAnime
import com.chs.youranimelist.domain.usecase.AnimeUseCases
import com.chs.youranimelist.domain.usecase.DeleteAnime
import com.chs.youranimelist.domain.usecase.GetAnimes
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
    fun provideAnimeRepository(db: AnimeListDatabase): AnimeListRepository {
        return AnimeListRepositoryImpl(db.animeListDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(repository: AnimeListRepository): AnimeUseCases {
        return AnimeUseCases(
            getAnime = GetAnimes(repository),
            deleteAnime = DeleteAnime(repository),
            addAnime = AddAnime(repository),
        )
    }
}