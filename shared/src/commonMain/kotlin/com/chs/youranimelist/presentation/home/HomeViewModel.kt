package com.chs.youranimelist.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.common.onError
import com.chs.common.onSuccess
import com.chs.youranimelist.domain.usecase.GetAnimeRecListUseCase
import com.chs.youranimelist.presentation.Util
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class HomeViewModel(
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
            HomeState()
        )

    private val _event: Channel<HomeEvent> = Channel()
    val event = _event.receiveAsFlow()

    private fun getHomeList() {
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
                        animeRecommendList = data,
                    )
                }
            }.onError { error ->
                _state.update { it.copy(isLoading = false) }

                _event.send(HomeEvent.ShowToast(error.message.toString()))
            }
        }
    }
}