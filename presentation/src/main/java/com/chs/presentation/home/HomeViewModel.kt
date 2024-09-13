package com.chs.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.GetAnimeRecListUseCase
import com.chs.presentation.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeListUseCase: GetAnimeRecListUseCase,
) : ViewModel() {

    var state: HomeState by mutableStateOf(HomeState())
        private set

    init {
        getHomeList()
    }

    fun changeOption(event: HomeEvent) {
        when (event) {
            HomeEvent.GetHomeData -> getHomeList()
        }
    }

    private fun getHomeList() {
        viewModelScope.launch {
            getHomeListUseCase(
                currentSeason = Util.getCurrentSeason(),
                nextSeason = Util.getNextSeason(),
                currentYear = Util.getCurrentYear(),
                nextYear = Util.getVariationYear(),
            ).collect { result ->
                state = state.copy(
                    animeRecommendList = result
                )
            }
        }
    }
}