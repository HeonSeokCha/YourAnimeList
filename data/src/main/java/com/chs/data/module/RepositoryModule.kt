package com.chs.data.module

import com.apollographql.apollo.ApolloClient
import com.chs.data.repository.ActorRepositoryImpl
import com.chs.data.repository.AnimeRepositoryImpl
import com.chs.data.repository.CharacterRepositoryImpl
import com.chs.data.repository.SearchRepositoryImpl
import com.chs.data.repository.StudioRepositoryImpl
import com.chs.data.source.JikanService
import com.chs.data.source.db.dao.AnimeListDao
import com.chs.data.source.db.dao.GenreDao
import com.chs.data.source.db.dao.TagDao
import com.chs.domain.repository.ActorRepository
import com.chs.domain.repository.AnimeRepository
import com.chs.domain.repository.CharacterRepository
import com.chs.domain.repository.SearchRepository
import com.chs.domain.repository.StudioRepository
import com.chs.domain.usecase.DeleteAnimeInfoUseCase
import com.chs.domain.usecase.DeleteCharaInfoUseCase
import com.chs.domain.usecase.DeleteSearchHistoryUseCase
import com.chs.domain.usecase.GetActorDetailInfoUseCase
import com.chs.domain.usecase.GetActorMediaListUseCase
import com.chs.domain.usecase.GetAnimeDetailRecListUseCase
import com.chs.domain.usecase.GetAnimeDetailUseCase
import com.chs.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.domain.usecase.GetAnimeRecListUseCase
import com.chs.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.domain.usecase.GetAnimeThemeUseCase
import com.chs.domain.usecase.GetCharaDetailAnimeListUseCase
import com.chs.domain.usecase.GetCharaDetailUseCase
import com.chs.domain.usecase.GetCharaSearchResultUseCase
import com.chs.domain.usecase.GetRecentGenresTagUseCase
import com.chs.domain.usecase.GetSaveTagUseCase
import com.chs.domain.usecase.GetSavedAnimeInfoUseCase
import com.chs.domain.usecase.GetSavedAnimeListUseCase
import com.chs.domain.usecase.GetSavedCharaInfoUseCase
import com.chs.domain.usecase.GetSavedCharaListUseCase
import com.chs.domain.usecase.GetSavedGenresUseCase
import com.chs.domain.usecase.GetSearchHistoryUseCase
import com.chs.domain.usecase.GetStudioAnimeListUseCase
import com.chs.domain.usecase.GetStudioDetailUseCase
import com.chs.domain.usecase.InsertAnimeInfoUseCase
import com.chs.domain.usecase.InsertCharaInfoUseCase
import com.chs.domain.usecase.InsertSearchHistoryUseCase
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val provideRepositoryModule = module {
    singleOf(::AnimeRepositoryImpl) { bind<AnimeRepository>() }
    singleOf(::CharacterRepositoryImpl) { bind<CharacterRepository>() }
    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    singleOf(::ActorRepositoryImpl) { bind<ActorRepository>() }

    factory { DeleteAnimeInfoUseCase(get()) }
    factory { GetRecentGenresTagUseCase(get()) }
    factory { GetAnimeFilteredListUseCase(get()) }
    factory { GetAnimeSearchResultUseCase(get()) }
    factory { DeleteCharaInfoUseCase(get()) }
    factory { GetActorMediaListUseCase(get()) }
    factory { GetCharaDetailUseCase(get()) }
    factory { GetAnimeThemeUseCase(get()) }
    factory { InsertSearchHistoryUseCase(get()) }
    factory { GetSavedAnimeInfoUseCase(get()) }
    factory { InsertCharaInfoUseCase(get()) }
    factory { DeleteSearchHistoryUseCase(get()) }
    factory { GetStudioAnimeListUseCase(get()) }
    factory { GetSavedCharaInfoUseCase(get()) }
    factory { GetAnimeDetailUseCase(get()) }
    factory { GetActorDetailInfoUseCase(get()) }
    factory { GetSavedAnimeListUseCase(get()) }
    factory { GetSearchHistoryUseCase(get()) }
    factory { GetAnimeRecListUseCase(get()) }
    factory { GetSaveTagUseCase(get()) }
    factory { GetSavedCharaListUseCase(get()) }
    factory { GetStudioDetailUseCase(get()) }
    factory { GetCharaSearchResultUseCase(get()) }
    factory { GetAnimeDetailRecListUseCase(get()) }
    factory { GetCharaDetailAnimeListUseCase(get()) }
    factory { InsertAnimeInfoUseCase(get()) }
    factory { GetSavedGenresUseCase(get()) }
}

@Module
class RepositoryModule {

    @Factory(binds = [AnimeRepository::class])
    fun todoRepository(
        apolloClient: ApolloClient,
        jikanService: JikanService,
        animeDao: AnimeListDao,
        genreDao: GenreDao,
        tagDao: TagDao
    ): AnimeRepositoryImpl {
        return AnimeRepositoryImpl(
            apolloClient = apolloClient,
            jikanService = jikanService,
            animeDao = animeDao,
            genreDao = genreDao,
            tagDao = tagDao
        )
    }
}