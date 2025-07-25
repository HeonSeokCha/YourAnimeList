package com.chs.youranimelist.di

import com.chs.youranimelist.data.repository.AnimeRepositoryImpl
import com.chs.youranimelist.data.repository.CharacterRepositoryImpl
import com.chs.youranimelist.data.repository.SearchRepositoryImpl
import com.chs.youranimelist.data.repository.StudioRepositoryImpl
import com.chs.youranimelist.domain.repository.AnimeRepository
import com.chs.youranimelist.domain.repository.CharacterRepository
import com.chs.youranimelist.domain.repository.SearchRepository
import com.chs.youranimelist.domain.repository.StudioRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::AnimeRepositoryImpl).bind<AnimeRepository>()
    singleOf(::CharacterRepositoryImpl).bind<CharacterRepository>()
    singleOf(::SearchRepositoryImpl).bind<SearchRepository>()
    singleOf(::StudioRepositoryImpl).bind<StudioRepository>()
}