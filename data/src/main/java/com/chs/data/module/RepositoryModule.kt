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
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val provideRepositoryModule = module {
    singleOf(::AnimeRepositoryImpl) { bind<AnimeRepository>() }
    singleOf(::CharacterRepositoryImpl) { bind<CharacterRepository>() }
    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    singleOf(::ActorRepositoryImpl) { bind<ActorRepository>() }
}