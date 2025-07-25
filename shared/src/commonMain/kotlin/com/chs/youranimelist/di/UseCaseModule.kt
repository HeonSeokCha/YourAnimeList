package com.chs.youranimelist.di

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
import org.koin.dsl.module

val useCaseModule = module {
    factory { DeleteAnimeInfoUseCase(get()) }
    factory { GetSavedCharaInfoUseCase(get()) }
    factory { GetStudioDetailUseCase(get()) }
    factory { GetActorDetailInfoUseCase(get()) }
    factory { GetSavedGenresUseCase(get()) }
    factory { GetSaveTagUseCase(get()) }
    factory { GetSavedAnimeInfoUseCase(get()) }
    factory { GetRecentGenresTagUseCase(get()) }
    factory { DeleteCharaInfoUseCase(get()) }
    factory { GetStudioAnimeListUseCase(get()) }
    factory { GetCharaDetailAnimeListUseCase(get()) }
    factory { InsertAnimeInfoUseCase(get()) }
    factory { GetAnimeDetailRecListUseCase(get()) }
    factory { GetCharaSearchResultUseCase(get()) }
    factory { GetAnimeThemeUseCase(get()) }
    factory { GetAnimeSearchResultUseCase(get()) }
    factory { GetAnimeFilteredListUseCase(get()) }
    factory { GetSavedCharaListUseCase(get()) }
    factory { GetCharaDetailUseCase(get()) }
    factory { GetAnimeRecListUseCase(get()) }
    factory { GetSavedAnimeListUseCase(get()) }
    factory { GetActorMediaListUseCase(get()) }
    factory { InsertSearchHistoryUseCase(get()) }
    factory { InsertCharaInfoUseCase(get()) }
    factory { DeleteSearchHistoryUseCase(get()) }
    factory { GetSearchHistoryUseCase(get()) }
    factory { GetAnimeDetailUseCase(get()) }
}