package com.chs.youranimelist.presentation.browse.studio

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.util.onError
import com.chs.youranimelist.util.onSuccess
import com.chs.youranimelist.domain.usecase.GetStudioAnimeListUseCase
import com.chs.youranimelist.domain.usecase.GetStudioDetailUseCase
import com.chs.youranimelist.presentation.browse.BrowseScreen
import com.chs.youranimelist.presentation.browse.studio.StudioDetailEffect.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudioDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getStudioDetailUseCase: GetStudioDetailUseCase,
    getStudioAnimeListUseCase: GetStudioAnimeListUseCase
) : ViewModel() {

    private val studioId: Int = savedStateHandle.toRoute<BrowseScreen.StudioDetail>().id
    private val sortState = MutableStateFlow(SortType.NEWEST)
    private val _effect: Channel<StudioDetailEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    private var _state = MutableStateFlow(StudioDetailState())
    val state = _state
        .onStart {
            getStudioDetailInfo()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            StudioDetailState()
        )

    val pagingData = sortState
        .flatMapLatest {
            getStudioAnimeListUseCase(
                studioId = studioId,
                studioSort = it.rawValue
            )
        }
        .cachedIn(viewModelScope)


    private fun getStudioDetailInfo() {
        viewModelScope.launch {
            getStudioDetailUseCase(studioId)
                .onSuccess { success ->
                    _state.update {
                        it.copy(
                            studioDetailInfo = success
                        )
                    }

                }.onError { error ->

                }
        }
    }

    fun handleIntent(intent: StudioDetailIntent) {
        when (intent) {

            is StudioDetailIntent.ClickAnime -> {
                _effect.trySend(
                    NavigateAnimeDetail(
                        id = intent.id,
                        idMal = intent.idMal
                    )
                )
            }

            is StudioDetailIntent.ClickSortOption -> {
                sortState.update { intent.value }
                _state.update { it.copy(sortOption = intent.value) }
            }

            StudioDetailIntent.OnLoading -> _state.update { it.copy(isPagingLoading = true) }
            StudioDetailIntent.OnLoadComplete -> _state.update { it.copy(isPagingLoading = false) }
            StudioDetailIntent.OnAppendLoading -> _state.update { it.copy(isPagingAppendLoading = true) }
            StudioDetailIntent.OnAppendLoadComplete -> _state.update { it.copy(isPagingAppendLoading = false) }
            StudioDetailIntent.ClickClose -> _effect.trySend(StudioDetailEffect.OnClose)
        }
    }
}