package com.chs.youranimelist.di

import com.chs.youranimelist.data.repository.AnimeDetailRepositoryImpl
import com.chs.youranimelist.data.repository.AnimeListRepositoryImpl
import com.chs.youranimelist.data.repository.CharacterRepositoryImpl
import com.chs.youranimelist.data.repository.SearchRepositoryImpl
import com.chs.youranimelist.domain.repository.AnimeDetailRepository
import com.chs.youranimelist.domain.repository.AnimeListRepository
import com.chs.youranimelist.domain.repository.CharacterRepository
import com.chs.youranimelist.domain.repository.SearchRepository
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

    @Binds
    abstract fun bindCharacterDetailRepository(
        characterDetailRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    abstract fun bindSearchRepository(
        searchRepository: SearchRepositoryImpl
    ): SearchRepository

}