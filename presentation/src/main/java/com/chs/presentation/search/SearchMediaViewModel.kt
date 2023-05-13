package com.chs.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.common.UiConst
import com.chs.domain.usecase.GetAnimeSearchResultUseCase
import com.chs.domain.usecase.GetCharaSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchMediaViewModel @Inject constructor(
    private val searchAnimeUseCase: GetAnimeSearchResultUseCase,
    private val searchCharaUseCase: GetCharaSearchResultUseCase
) : ViewModel() {

    var searchPage: String = ""
    private val _state = MutableStateFlow(SearchMediaState())
    val state = _state.asStateFlow()


    fun search(query: String) {
        _state.update {
            when (searchPage) {
                UiConst.TARGET_ANIME -> {
                    it.copy(
                        searchAnimeResultPaging = searchAnimeUseCase(query).cachedIn(viewModelScope)
                    )
                }

//            UiConst.TARGET_MANGA -> {
//                state = state.copy(
//                    searchMangaResultPaging = searchMangaUseCase(query).cachedIn(viewModelScope)
//                )
//            }

                UiConst.TARGET_CHARA -> {
                    it.copy(
                        searchCharaResultPaging = searchCharaUseCase(query).cachedIn(viewModelScope)
                    )
                }

                else -> {
                    it
                }
            }
        }
    }
}