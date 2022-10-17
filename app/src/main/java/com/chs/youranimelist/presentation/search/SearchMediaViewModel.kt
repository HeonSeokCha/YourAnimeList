package com.chs.youranimelist.presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn

import com.chs.youranimelist.domain.usecase.SearchAnimeUseCase
import com.chs.youranimelist.domain.usecase.SearchCharaUseCase
import com.chs.youranimelist.domain.usecase.SearchMangaUseCase
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            when (searchPage) {
                Constant.TARGET_ANIME -> {
                    searchAnimeUseCase(query).cachedIn(viewModelScope)
                }

                Constant.TARGET_MANGA -> {
                    searchMangaUseCase(query).cachedIn(viewModelScope)
                }

                Constant.TARGET_CHARA -> {
                    searchCharaUseCase(query).cachedIn(viewModelScope)
                }
                else -> {
                    return@launch
                }
            }
        }
    }
}