package com.chs.youranimelist.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.chs.youranimelist.domain.usecase.SearchAnimeUseCase
import com.chs.youranimelist.domain.usecase.SearchCharaUseCase
import com.chs.youranimelist.domain.usecase.SearchMangaUseCase
import com.chs.youranimelist.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMediaViewModel @Inject constructor(
    private val searchAnimeUseCase: SearchAnimeUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
    private val searchCharaUseCase: SearchCharaUseCase
) : ViewModel() {

    var searchPage: String = ""
    var state by mutableStateOf(SearchState())


    fun search(query: String) {
        when (searchPage) {
            Constant.TARGET_ANIME -> {
                state = state.copy(
                    searchAnimeResultPaging = searchAnimeUseCase(query).cachedIn(viewModelScope)
                )
            }

            Constant.TARGET_MANGA -> {
                state = state.copy(
                    searchMangaResultPaging = searchMangaUseCase(query).cachedIn(viewModelScope)
                )
            }

            Constant.TARGET_CHARA -> {
                state = state.copy(
                    searchCharaResultPaging = searchCharaUseCase(query).cachedIn(viewModelScope)
                )
            }
        }
    }
}