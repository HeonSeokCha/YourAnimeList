package com.chs.youranimelist.presentation.browse.actor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.youranimelist.domain.model.SortType
import com.chs.youranimelist.util.onError
import com.chs.youranimelist.util.onSuccess
import com.chs.youranimelist.domain.usecase.GetActorMediaListUseCase
import com.chs.youranimelist.domain.usecase.GetActorDetailInfoUseCase
import com.chs.youranimelist.presentation.browse.BrowseScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActorDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCase,
    getActorMediaListUseCase: GetActorMediaListUseCase
) : ViewModel() {

    private val actorId: Int = savedStateHandle.toRoute<BrowseScreen.ActorDetail>().id
    private val _state = MutableStateFlow(ActorDetailState())
    val state = _state
        .onStart {
            getActorDetailInfo(actorId)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ActorDetailState()
        )
    private val sortState = MutableStateFlow(SortType.NEWEST)

    private val _effect: Channel<ActorDetailEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    val pagingData = sortState
        .flatMapLatest {
            getActorMediaListUseCase(
                actorId = actorId,
                sortOptions = listOf(it.rawValue)
            )
        }
        .cachedIn(viewModelScope)

    fun handleIntent(intent: ActorDetailIntent) {
        when (intent) {
            is ActorDetailIntent.ClickAnime -> {
                _effect.trySend(
                    ActorDetailEffect.NavigateAnimeDetail(
                        id = intent.id,
                        idMal = intent.idMal
                    )
                )
            }

            is ActorDetailIntent.ClickChara -> {
                _effect.trySend(ActorDetailEffect.NavigateCharaDetail(id = intent.id))
            }

            ActorDetailIntent.ClickClose -> _effect.trySend(ActorDetailEffect.NavigateClose)
            is ActorDetailIntent.ClickLink -> {
                _effect.trySend(
                    ActorDetailEffect.NavigateBrowser(
                        intent.url
                    )
                )
            }

            is ActorDetailIntent.ClickTab -> _state.update { it.copy(tabIdx = intent.idx) }
            is ActorDetailIntent.ChangeSortOption -> {
                _state.update { it.copy(selectOption = intent.option) }
                sortState.update { intent.option }
            }

            ActorDetailIntent.OnLoad -> _state.update { it.copy(isLoading = true) }
            ActorDetailIntent.OnLoadComplete -> _state.update { it.copy(isLoading = false) }
            ActorDetailIntent.OnAppendLoad -> _state.update { it.copy(isAppendLoading = true) }
            ActorDetailIntent.OnAppendLoadComplete -> _state.update { it.copy(isAppendLoading = false) }
            ActorDetailIntent.OnError -> Unit
        }
    }

    private fun getActorDetailInfo(actorId: Int) {
        viewModelScope.launch {
            getActorDetailInfoUseCase(actorId)
                .onSuccess { success ->
                    _state.update { it.copy(actorDetailInfo = success) }
                }.onError { error ->
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }
}
