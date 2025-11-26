package com.chs.youranimelist.presentation.bottom.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.model.CategoryType
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.util.onError
import com.chs.youranimelist.util.onSuccess
import com.chs.youranimelist.domain.usecase.GetAnimeRecListUseCase
import com.chs.youranimelist.domain.usecase.GetRecentGenresTagUseCase
import com.chs.youranimelist.presentation.Util
import com.chs.youranimelist.presentation.bottom.home.HomeEffect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val getHomeListUseCase: GetAnimeRecListUseCase,
    private val getRecentGenresTagUseCase: GetRecentGenresTagUseCase
) : ViewModel() {

    private var getAnimeJob: Job? = null
    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart { getHomeList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect: Channel<HomeEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.ClickAnime -> {
                _effect.trySend(
                    NavigateAnimeDetail(
                        id = intent.id,
                        idMal = intent.idMal
                    )
                )
            }

            is HomeIntent.ClickCategory -> {
                when (intent.categoryType) {
                    CategoryType.TRENDING -> {
                        _effect.trySend(
                            NavigateSort(
                                option = listOf(
                                    SortType.TRENDING,
                                    SortType.POPULARITY
                                )
                            )
                        )
                    }

                    CategoryType.POPULAR -> {
                        _effect.trySend(
                            NavigateSort(
                                year = Util.getCurrentYear(),
                                season = Util.getCurrentSeason()
                            )
                        )
                    }

                    CategoryType.UPCOMMING -> {
                        _effect.trySend(
                            NavigateSort(
                                year = Util.getVariationYear(),
                                season = Util.getNextSeason()
                            )
                        )
                    }

                    CategoryType.ALLTIME -> {
                        _effect.trySend(
                            NavigateSort(
                                option = listOf(SortType.POPULARITY)
                            )
                        )
                    }

                    CategoryType.TOP -> {
                        _effect.trySend(NavigateSort(option = listOf(SortType.AVERAGE)))
                    }
                }
            }
        }
    }

    private fun getHomeList() {
        getAnimeJob?.cancel()
        getAnimeJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            withContext(Dispatchers.IO) {
                getRecentGenreTag()
            }

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
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

    private suspend fun getRecentGenreTag() {
        getRecentGenresTagUseCase()
    }

    override fun onCleared() {
        getAnimeJob?.cancel()
        super.onCleared()
    }
}