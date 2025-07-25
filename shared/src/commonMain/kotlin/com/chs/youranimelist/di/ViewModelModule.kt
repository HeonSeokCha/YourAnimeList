package com.chs.youranimelist.di

import com.chs.youranimelist.presentation.animeList.AnimeListViewModel
import com.chs.youranimelist.presentation.browse.actor.ActorDetailViewModel
import com.chs.youranimelist.presentation.browse.anime.AnimeDetailViewModel
import com.chs.youranimelist.presentation.browse.character.CharacterDetailViewModel
import com.chs.youranimelist.presentation.browse.studio.StudioDetailViewModel
import com.chs.youranimelist.presentation.charaList.CharacterListViewModel
import com.chs.youranimelist.presentation.home.HomeViewModel
import com.chs.youranimelist.presentation.main.MainViewModel
import com.chs.youranimelist.presentation.search.SearchViewModel
import com.chs.youranimelist.presentation.sortList.SortedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get(), get())}
    viewModel { HomeViewModel(get()) }
    viewModelOf(::AnimeListViewModel)
    viewModelOf(::AnimeDetailViewModel)
    viewModelOf(::ActorDetailViewModel)
    viewModelOf(::CharacterDetailViewModel)
    viewModelOf(::StudioDetailViewModel)
    viewModelOf(::CharacterListViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SortedViewModel)
}