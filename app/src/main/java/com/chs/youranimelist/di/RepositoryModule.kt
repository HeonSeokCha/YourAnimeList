package com.chs.youranimelist.di

import com.chs.youranimelist.data.repository.AnimeDetailRepositoryImpl
import com.chs.youranimelist.data.repository.AnimeListRepositoryImpl
import com.chs.youranimelist.domain.repository.AnimeDetailRepository
import com.chs.youranimelist.domain.repository.AnimeListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAnimeRepository(
        animeListRepositoryImpl: AnimeListRepositoryImpl
    ): AnimeListRepository

    @Binds
    abstract fun bindAnimeDetailRepository(
        animeDetailRepositoryImpl: AnimeDetailRepositoryImpl
    ): AnimeDetailRepository

}