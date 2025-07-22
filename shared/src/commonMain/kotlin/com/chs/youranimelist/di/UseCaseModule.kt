package com.chs.youranimelist.di

import com.chs.youranimelist.domain.repository.ActorRepository
import com.chs.youranimelist.domain.repository.AnimeRepository
import com.chs.youranimelist.domain.repository.CharacterRepository
import com.chs.youranimelist.domain.repository.SearchRepository
import com.chs.youranimelist.domain.repository.StudioRepository
import com.chs.youranimelist.domain.usecase.DeleteAnimeInfoUseCase
import com.chs.youranimelist.domain.usecase.DeleteCharaInfoUseCase
import com.chs.youranimelist.domain.usecase.DeleteSearchHistoryUseCase
import com.chs.youranimelist.domain.usecase.GetActorDetailInfoUseCase
import com.chs.youranimelist.domain.usecase.GetActorMediaListUseCase
import com.chs.youranimelist.domain.usecase.GetAnimeDetailRecListUseCase
import com.chs.youranimelist.domain.usecase.GetAnimeDetailUseCase
import com.chs.youranimelist.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.youranimelist.domain.usecase.GetAnimeRecListUseCase
import com.chs.youranimelist.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.youranimelist.domain.usecase.GetAnimeThemeUseCase
import com.chs.youranimelist.domain.usecase.GetCharaDetailAnimeListUseCase
import com.chs.youranimelist.domain.usecase.GetCharaDetailUseCase
import com.chs.youranimelist.domain.usecase.GetCharaSearchResultUseCase
import com.chs.youranimelist.domain.usecase.GetRecentGenresTagUseCase
import com.chs.youranimelist.domain.usecase.GetSaveTagUseCase
import com.chs.youranimelist.domain.usecase.GetSavedAnimeInfoUseCase
import com.chs.youranimelist.domain.usecase.GetSavedAnimeListUseCase
import com.chs.youranimelist.domain.usecase.GetSavedCharaInfoUseCase
import com.chs.youranimelist.domain.usecase.GetSavedCharaListUseCase
import com.chs.youranimelist.domain.usecase.GetSavedGenresUseCase
import com.chs.youranimelist.domain.usecase.GetSearchHistoryUseCase
import com.chs.youranimelist.domain.usecase.GetStudioAnimeListUseCase
import com.chs.youranimelist.domain.usecase.GetStudioDetailUseCase
import com.chs.youranimelist.domain.usecase.InsertAnimeInfoUseCase
import com.chs.youranimelist.domain.usecase.InsertCharaInfoUseCase
import com.chs.youranimelist.domain.usecase.InsertSearchHistoryUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class UseCaseModule {

    @Factory
    fun provideDeleteAnimeInfoUseCase(repository: AnimeRepository): DeleteAnimeInfoUseCase {
        return DeleteAnimeInfoUseCase(repository)
    }

    @Factory
    fun provideGetSavedCharaInfoUseCase(repository: CharacterRepository): GetSavedCharaInfoUseCase {
        return GetSavedCharaInfoUseCase(repository)
    }

    @Factory
    fun provideGetStudioDetailUseCase(repository: StudioRepository): GetStudioDetailUseCase {
        return GetStudioDetailUseCase(repository)
    }

    @Factory
    fun provideGetActorDetailInfoUseCase(repository: ActorRepository): GetActorDetailInfoUseCase {
        return GetActorDetailInfoUseCase(repository)
    }

    @Factory
    fun provideGetSavedGenresUseCase(repository: AnimeRepository): GetSavedGenresUseCase {
        return GetSavedGenresUseCase(repository)
    }

    @Factory
    fun provideGetSaveTagUseCase(repository: AnimeRepository): GetSaveTagUseCase {
        return GetSaveTagUseCase(repository)
    }

    @Factory
    fun provideGetSavedAnimeInfoUseCase(repository: AnimeRepository): GetSavedAnimeInfoUseCase {
        return GetSavedAnimeInfoUseCase(repository)
    }

    @Factory
    fun provideGetRecentGenresTagUseCase(repository: AnimeRepository): GetRecentGenresTagUseCase {
        return GetRecentGenresTagUseCase(repository)
    }

    @Factory
    fun provideDeleteCharaInfoUseCase(repository: CharacterRepository): DeleteCharaInfoUseCase {
        return DeleteCharaInfoUseCase(repository)
    }

    @Factory
    fun provideGetStudioAnimeListUseCase(repository: StudioRepository): GetStudioAnimeListUseCase {
        return GetStudioAnimeListUseCase(repository)
    }

    @Factory
    fun provideGetCharaDetailAnimeListUseCase(repository: CharacterRepository): GetCharaDetailAnimeListUseCase {
        return GetCharaDetailAnimeListUseCase(repository)
    }

    @Factory
    fun provideInsertAnimeInfoUseCase(repository: AnimeRepository): InsertAnimeInfoUseCase {
        return InsertAnimeInfoUseCase(repository)
    }

    @Factory
    fun provideGetAnimeDetailRecListUseCase(repository: AnimeRepository): GetAnimeDetailRecListUseCase {
        return GetAnimeDetailRecListUseCase(repository)
    }

    @Factory
    fun provideGetCharaSearchResultUseCase(repository: SearchRepository): GetCharaSearchResultUseCase {
        return GetCharaSearchResultUseCase(repository)
    }

    @Factory
    fun provideGetAnimeThemeUseCase(repository: AnimeRepository): GetAnimeThemeUseCase {
        return GetAnimeThemeUseCase(repository)
    }

    @Factory
    fun provideGetAnimeSearchResultUseCase(repository: SearchRepository): GetAnimeSearchResultUseCase {
        return GetAnimeSearchResultUseCase(repository)
    }

    @Factory
    fun provideGetAnimeFilteredListUseCase(repository: AnimeRepository): GetAnimeFilteredListUseCase {
        return GetAnimeFilteredListUseCase(repository)
    }

    @Factory
    fun provideGetSavedCharaListUseCase(repository: CharacterRepository): GetSavedCharaListUseCase {
        return GetSavedCharaListUseCase(repository)
    }

    @Factory
    fun provideGetCharaDetailUseCase(repository: CharacterRepository): GetCharaDetailUseCase {
        return GetCharaDetailUseCase(repository)
    }

    @Factory
    fun provideGetAnimeRecListUseCase(repository: AnimeRepository): GetAnimeRecListUseCase {
        return GetAnimeRecListUseCase(repository)
    }

    @Factory
    fun provideGetSavedAnimeListUseCase(repository: AnimeRepository): GetSavedAnimeListUseCase {
        return GetSavedAnimeListUseCase(repository)
    }

    @Factory
    fun provideGetActorMediaListUseCase(repository: ActorRepository): GetActorMediaListUseCase {
        return GetActorMediaListUseCase(repository)
    }

    @Factory
    fun provideInsertSearchHistoryUseCase(repository: SearchRepository): InsertSearchHistoryUseCase {
        return InsertSearchHistoryUseCase(repository)
    }

    @Factory
    fun provideInsertCharaInfoUseCase(repository: CharacterRepository): InsertCharaInfoUseCase {
        return InsertCharaInfoUseCase(repository)
    }

    @Factory
    fun provideDeleteSearchHistoryUseCase(repository: SearchRepository): DeleteSearchHistoryUseCase {
        return DeleteSearchHistoryUseCase(repository)
    }

    @Factory
    fun provideGetSearchHistoryUseCase(repository: SearchRepository): GetSearchHistoryUseCase {
        return GetSearchHistoryUseCase(repository)
    }

    @Factory
    fun provideGetAnimeDetailUseCase(repository: AnimeRepository): GetAnimeDetailUseCase {
        return GetAnimeDetailUseCase(repository)
    }
}