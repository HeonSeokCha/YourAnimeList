package com.chs.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.model.onError
import com.chs.domain.model.onSuccess
import com.chs.domain.usecase.GetAnimeRecListUseCase
import com.chs.presentation.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeListUseCase: GetAnimeRecListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            getHomeList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun changeOption(event: HomeEvent) {
        when (event) {
            HomeEvent.GetHomeData -> getHomeList()
            HomeEvent.OnRefresh -> {
                _state.update { it.copy(isRefreshing = true) }
                getHomeList()
            }

            else -> Unit
        }
    }

    private fun getHomeList() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getHomeListUseCase(
                currentSeason = Util.getCurrentSeason(),
                nextSeason = Util.getNextSeason(),
                currentYear = Util.getCurrentYear(),
                variationYear = Util.getVariationYear()
            ).onSuccess { data ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        animeRecommendList = data,
                    )
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.message,
                        isLoading = false,
                        isRefreshing = false
                    )
                }
            }
        }
    }
}