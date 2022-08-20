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
import javax.inject.Inject

@HiltViewModel
class SearchMediaViewModel @Inject constructor(
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
                                state = state.copy(isLoading = result.isLoading)
                            }
                            is Resource.Success -> {
                                hasNextPage = result.data?.page?.pageInfo?.hasNextPage ?: false
                                result.data?.page?.media?.forEach { anime ->
                                    state.searchAnimeResult.add(anime)
                                }
                                state = state.copy(
                                    isLoading = false,
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
                                state = state.copy(isLoading = result.isLoading)
                            }
                            is Resource.Success -> {
                                hasNextPage = result.data?.page?.pageInfo?.hasNextPage ?: false
                                result.data?.page?.media?.forEach { manga ->
                                    state.searchMangaResult.add(manga)
                                }
                                state = state.copy(
                                    isLoading = false,
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
                                state = state.copy(isLoading = result.isLoading)
                            }
                            is Resource.Success -> {
                                hasNextPage = result.data?.page?.pageInfo?.hasNextPage ?: false
                                result.data?.page?.characters?.forEach { characters ->
                                    state.searchCharaResult.add(characters)
                                }
                                state = state.copy(
                                    isLoading = false,
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