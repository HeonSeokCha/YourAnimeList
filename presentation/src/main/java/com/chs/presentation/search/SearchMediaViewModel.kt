package com.chs.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.common.UiConst
import com.chs.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.domain.usecase.GetCharaSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchMediaViewModel @Inject constructor(
    private val searchAnimeUseCase: GetAnimeSearchResultUseCase,
    private val searchCharaUseCase: GetCharaSearchResultUseCase
) : ViewModel() {

    var searchPage: String = ""
    var state by mutableStateOf(SearchState())


    fun search(query: String) {

        when (searchPage) {
            UiConst.TARGET_ANIME -> {
                state = state.copy(
                    searchAnimeResultPaging = searchAnimeUseCase(query).cachedIn(viewModelScope)
                )
            }

//            UiConst.TARGET_MANGA -> {
//                state = state.copy(
//                    searchMangaResultPaging = searchMangaUseCase(query).cachedIn(viewModelScope)
//                )
//            }

            UiConst.TARGET_CHARA -> {
                state = state.copy(
                    searchCharaResultPaging = searchCharaUseCase(query).cachedIn(viewModelScope)
                )
            }
        }
    }
}