package com.chs.youranimelist.di

import com.chs.data.repository.ActorRepositoryImpl
import com.chs.data.repository.AnimeRepositoryImpl
import com.chs.data.repository.CharacterRepositoryImpl
import com.chs.data.repository.SearchRepositoryImpl
import com.chs.data.repository.StudioRepositoryImpl
import com.chs.data.source.JikanService
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.CharaListDao
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.dao.SearchListDao
import com.chs.data.source.db.dao.TagDao
import com.chs.youranimelist.domain.repository.ActorRepository
import com.chs.youranimelist.domain.repository.AnimeRepository
import com.chs.youranimelist.domain.repository.CharacterRepository
import com.chs.youranimelist.domain.repository.SearchRepository
import com.chs.youranimelist.domain.repository.StudioRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class RepositoryModule {
    @Factory(binds = [AnimeRepository::class])
    fun provideAnimeRepository(
        apolloClient: ApolloClient,
        jikanService: JikanService,
        animeDao: AnimeListDao,
        genreDao: GenreDao,
        tagDao: TagDao
    ): AnimeRepositoryImpl {
        return AnimeRepositoryImpl(apolloClient, jikanService, animeDao, genreDao, tagDao)
    }

    @Factory(binds = [CharacterRepository::class])
    fun provideCharacterRepository(
        apolloClient: ApolloClient,
        dao: CharaListDao
    ): CharacterRepositoryImpl = CharacterRepositoryImpl(apolloClient, dao)

    @Factory(binds = [SearchRepository::class])
    fun provideSearchRepository(
        apolloClient: ApolloClient,
        dao: SearchListDao
    ): SearchRepositoryImpl = SearchRepositoryImpl(apolloClient, dao)

    @Factory(binds = [ActorRepository::class])
    fun provideActorRepository(
        apolloClient: ApolloClient
    ): ActorRepositoryImpl = ActorRepositoryImpl(apolloClient)

    @Factory(binds = [StudioRepository::class])
    fun provideStudioRepository(
        apolloClient: ApolloClient
    ): StudioRepositoryImpl = StudioRepositoryImpl(apolloClient)
}