package com.chs.presentation

import com.chs.presentation.animeList.AnimeListViewModel
import com.chs.presentation.browse.actor.ActorDetailViewModel
import com.chs.presentation.browse.anime.AnimeDetailViewModel
import com.chs.presentation.browse.character.CharacterDetailViewModel
import com.chs.presentation.browse.studio.StudioDetailViewModel
import com.chs.presentation.charaList.CharacterListViewModel
import com.chs.presentation.home.HomeViewModel
import com.chs.presentation.main.MainViewModel
import com.chs.presentation.search.SearchViewModel
import com.chs.presentation.sortList.SortedViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val providePresentationModule = module {
    viewModelOf(::AnimeListViewModel)
    viewModelOf(::ActorDetailViewModel)
    viewModelOf(::AnimeDetailViewModel)
    viewModelOf(::CharacterDetailViewModel)
    viewModelOf(::StudioDetailViewModel)
    viewModelOf(::CharacterListViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::SortedViewModel)
    viewModelOf(::MainViewModel)
}
