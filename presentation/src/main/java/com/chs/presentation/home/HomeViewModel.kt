package com.chs.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.common.Resource
import com.chs.presentation.UiConst
import com.chs.domain.usecase.GetAnimeRecListUseCase
import com.chs.domain.usecase.GetRecentGenresUseCase
import com.chs.presentation.Util
import com.chs.presentation.main.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeListUseCase: GetAnimeRecListUseCase,
) : ViewModel() {

    var state: HomeState = HomeState()
        private set

    init {
        viewModelScope.launch {
            getHomeListUseCase(
                currentSeason = Util.getCurrentSeason(),
                nextSeason = Util.getNextSeason(),
                currentYear = Util.getCurrentYear(),
                nextYear = Util.getVariationYear(true),
                lastYear = Util.getVariationYear(false)
            ).collect { result ->
                state = when (result) {
                    is Resource.Loading -> {
                        state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state.copy(
                            animeRecommendList = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        state.copy(
                            isError = result.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }
}