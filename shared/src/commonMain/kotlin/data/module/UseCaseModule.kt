package com.chs.data.module

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