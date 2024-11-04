package com.chs.data.module

import com.chs.data.repository.ActorRepositoryImpl
import com.chs.data.repository.AnimeRepositoryImpl
import com.chs.data.repository.CharacterRepositoryImpl
import com.chs.data.repository.SearchRepositoryImpl
import com.chs.data.repository.StudioRepositoryImpl
import com.chs.domain.repository.ActorRepository
import com.chs.domain.repository.AnimeRepository
import com.chs.domain.repository.CharacterRepository
import com.chs.domain.repository.SearchRepository
import com.chs.domain.repository.StudioRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAnimeRepository(
        animeRepositoryImpl: AnimeRepositoryImpl
    ): AnimeRepository

    @Binds
    abstract fun bindCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ): CharacterRepository

    @Binds
    abstract fun bindStudioRepository(
        studioRepositoryImpl: StudioRepositoryImpl
    ): StudioRepository

    @Binds
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    abstract fun bindActorRepository(
        actorRepositoryImpl: ActorRepositoryImpl
    ): ActorRepository

}