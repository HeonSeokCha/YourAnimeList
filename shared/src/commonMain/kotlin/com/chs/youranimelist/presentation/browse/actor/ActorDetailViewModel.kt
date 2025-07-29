package com.chs.youranimelist.presentation.browse.actor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import app.cash.paging.cachedIn
import com.chs.youranimelist.util.onError
import com.chs.youranimelist.util.onSuccess
import com.chs.youranimelist.domain.usecase.GetActorMediaListUseCase
import com.chs.youranimelist.domain.usecase.GetActorDetailInfoUseCase
import com.chs.youranimelist.presentation.UiConst
import com.chs.youranimelist.presentation.browse.BrowseScreen
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ActorDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCase,
    private val getActorMediaListUseCase: GetActorMediaListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ActorDetailState())
    val state = _state
        .onStart {
            getActorDetailInfo(actorId)
            getActorAnimeList(actorId)
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            ActorDetailState()
        )

    private val _actorEvent: Channel<ActorDetailEvent> = Channel()
    val actorEvent = _actorEvent.receiveAsFlow()

    private val actorId: Int = savedStateHandle.toRoute<BrowseScreen.ActorDetail>().id

    fun changeEvent(event: ActorDetailEvent) {
        when (event) {
            is ActorDetailEvent.ChangeSortOption -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                        selectOption = UiConst.SortType.entries.find { it.rawValue == event.option }
                            ?: UiConst.SortType.NEWEST
                    )
                }

                getActorAnimeList(actorId)
            }

            is ActorDetailEvent.ClickBtn.TabIdx -> {
                _state.update { it.copy(tabIdx = event.idx) }
            }

            else -> Unit
        }
    }

    private fun getActorDetailInfo(actorId: Int) {
        viewModelScope.launch {
            getActorDetailInfoUseCase(actorId)
                .onSuccess { success ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            actorDetailInfo = success
                        )
                    }
                }.onError { error ->
                    _state.update { it.copy(isLoading = false) }
                    _actorEvent.send(ActorDetailEvent.OnError)
                }
        }
    }

    private fun getActorAnimeList(actorId: Int) {
        _state.update {
            it.copy(
                actorAnimeList = getActorMediaListUseCase(
                    actorId = actorId,
                    sortOptions = listOf(state.value.selectOption.rawValue)
                ).cachedIn(viewModelScope)
            )
        }
    }
}
