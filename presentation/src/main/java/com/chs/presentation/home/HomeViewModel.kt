package com.chs.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.common.UiConst
import com.chs.domain.usecase.GetAnimeRecListUseCase
import com.chs.presentation.ConvertDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeListUseCase: GetAnimeRecListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    val animeCategorySortList: List<Pair<String, Triple<UiConst.SortType, Int?, String?>>> =
        listOf(
            "TRENDING NOW" to Triple(
                UiConst.SortType.TRENDING,
                null,
                null
            ),
            "POPULAR THIS SEASON" to Triple(
                UiConst.SortType.POPULARITY,
                ConvertDate.getCurrentYear(),
                ConvertDate.getCurrentSeason()
            ),
            "UPCOMING NEXT SEASON" to Triple(
                UiConst.SortType.POPULARITY,
                ConvertDate.getVariationYear(true),
                ConvertDate.getNextSeason()
            ),
            "ALL TIME POPULAR" to Triple(
                UiConst.SortType.POPULARITY,
                null,
                null
            ),
            "TOP ANIME" to Triple(
                UiConst.SortType.AVERAGE,
                null,
                null
            )
        )

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            _state.update {
                it.copy(
                    animeRecommendList = getHomeListUseCase(
                        currentSeason = ConvertDate.getCurrentSeason(),
                        nextSeason = ConvertDate.getNextSeason(),
                        currentYear = ConvertDate.getCurrentYear(),
                        nextYear = ConvertDate.getVariationYear(true),
                        lastYear = ConvertDate.getVariationYear(false)
                    ),
                    isLoading = false
                )
            }
        }
    }
}