package com.chs.youranimelist.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.chs.youranimelist.domain.usecase.SearchAnimeUseCase
import com.chs.youranimelist.domain.usecase.SearchCharaUseCase
import com.chs.youranimelist.domain.usecase.SearchMangaUseCase
import com.chs.youranimelist.util.Constant
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class SearchMediaViewModel(
    private val searchAnimeUseCase: SearchAnimeUseCase,
    private val searchMangaUseCase: SearchMangaUseCase,
    private val searchCharaUseCase: SearchCharaUseCase
) : ViewModel() {

    var page: Int = 1
    var searchPage: String = ""
    var hasNextPage: Boolean = false

    var state by mutableStateOf(SearchState())


    fun search(query: String) {
        viewModelScope.launch {
            when (searchPage) {
                Constant.TARGET_ANIME -> {
                    searchAnimeUseCase(page, query).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(isLoading = true)
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    isLoading = false,
                                    searchAnimeResult = result.data?.page?.media!!
                                )
                            }
                            is Resource.Error -> {
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
                Constant.TARGET_MANGA -> {
                    searchMangaUseCase(page, query).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(isLoading = true)
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    isLoading = false,
                                    searchMangaResult = result.data?.page?.media!!
                                )
                            }
                            is Resource.Error -> {
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
                Constant.TARGET_CHARA -> {
                    searchCharaUseCase(page, query).collect { result ->
                        when (result) {
                            is Resource.Loading -> {
                                state = state.copy(isLoading = true)
                            }
                            is Resource.Success -> {
                                state = state.copy(
                                    isLoading = false,
                                    searchCharaResult = result.data?.page?.characters!!
                                )
                            }
                            is Resource.Error -> {
                                state = state.copy(
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}