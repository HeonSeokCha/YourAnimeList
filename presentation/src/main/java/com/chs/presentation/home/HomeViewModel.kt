package com.chs.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.common.Resource
import com.chs.presentation.UiConst
import com.chs.domain.usecase.GetAnimeRecListUseCase
import com.chs.domain.usecase.GetRecentGenresUseCase
import com.chs.presentation.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeListUseCase: GetAnimeRecListUseCase,
    private val getRecentGenresUseCase: GetRecentGenresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    val animeCategorySortList: List<Pair<String, Triple<UiConst.SortType, Int, String?>>> =
        listOf(
            "TRENDING NOW" to Triple(
                UiConst.SortType.TRENDING,
                0,
                null
            ),
            "POPULAR THIS SEASON" to Triple(
                UiConst.SortType.POPULARITY,
                Util.getCurrentYear(),
                Util.getCurrentSeason()
            ),
            "UPCOMING NEXT SEASON" to Triple(
                UiConst.SortType.POPULARITY,
                Util.getVariationYear(true),
                Util.getNextSeason()
            ),
            "ALL TIME POPULAR" to Triple(
                UiConst.SortType.POPULARITY,
                0,
                null
            ),
            "TOP ANIME" to Triple(
                UiConst.SortType.AVERAGE,
                0,
                null
            )
        )

    init {
        viewModelScope.launch {
            getHomeListUseCase(
                currentSeason = Util.getCurrentSeason(),
                nextSeason = Util.getNextSeason(),
                currentYear = Util.getCurrentYear(),
                nextYear = Util.getVariationYear(true),
                lastYear = Util.getVariationYear(false)
            ).collect { result ->
                _state.update {
                    when (result) {
                        is Resource.Loading -> {
                            it.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            it.copy(
                                animeRecommendList = result.data,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            it.copy(
                                isError = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            getRecentGenresUseCase()
        }
    }
}