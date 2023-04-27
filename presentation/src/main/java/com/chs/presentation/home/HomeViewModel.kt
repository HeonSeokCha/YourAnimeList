package com.chs.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.common.UiConst
import com.chs.domain.usecase.GetAnimeRecListUseCase
import com.chs.presentation.Util
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
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            _state.update {
                it.copy(
                    animeRecommendList = getHomeListUseCase(
                        currentSeason = Util.getCurrentSeason(),
                        nextSeason = Util.getNextSeason(),
                        currentYear = Util.getCurrentYear(),
                        nextYear = Util.getVariationYear(true),
                        lastYear = Util.getVariationYear(false)
                    ),
                    isLoading = false
                )
            }
        }
    }
}