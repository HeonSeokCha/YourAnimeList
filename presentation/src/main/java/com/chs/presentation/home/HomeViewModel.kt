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

    val animeCategorySortList = listOf(
        "TRENDING NOW" to (UiConst.SortType.TRENDING to "0"),
        "POPULAR THIS SEASON" to (UiConst.SortType.POPULARITY to ConvertDate.getCurrentSeason()),
        "UPCOMING NEXT SEASON" to (UiConst.SortType.POPULARITY to ConvertDate.getNextSeason()),
        "ALL TIME POPULAR" to (UiConst.SortType.POPULARITY to "0"),
        "TOP ANIME" to (UiConst.SortType.AVERAGE to "0")
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