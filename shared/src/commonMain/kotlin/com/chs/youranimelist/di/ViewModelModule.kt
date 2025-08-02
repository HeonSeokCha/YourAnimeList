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
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        HomeViewModel(
            get()
        )
    }
    viewModel {
        AnimeListViewModel(
            get()
        )
    }
    viewModel {
        AnimeDetailViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        ActorDetailViewModel(
            get(),
            get(),
            get()
        )
    }
    viewModel {
        CharacterDetailViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    viewModel {
        StudioDetailViewModel(
            get(),
            get(),
            get()
        )
    }
    viewModel {
        CharacterListViewModel(
            get()
        )
    }
    viewModel {
        SearchViewModel(
            get(),
            get()
        )
    }
    viewModel {
        SortedViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}