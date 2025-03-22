package com.chs.presentation.browse.actor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.common.onError
import com.chs.common.onSuccess
import com.chs.domain.usecase.GetActorMediaListUseCase
import com.chs.domain.usecase.GetActorDetailInfoUseCase
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCase,
    private val getActorMediaListUseCase: GetActorMediaListUseCase,
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
            _state.value
        )

    private val actorId: Int = savedStateHandle.toRoute<BrowseScreen.ActorDetail>().id

    fun changeEvent(event: ActorDetailEvent) {
        when (event) {
            is ActorDetailEvent.GetActorDetailInfo -> {
                getActorDetailInfo(actorId)
                getActorAnimeList(actorId)
            }

            is ActorDetailEvent.ChangeSortOption -> {
                _state.update {
                    it.copy(
                        selectOption = UiConst.SortType.entries.find { it.rawValue == event.option }
                            ?: UiConst.SortType.NEWEST
                    )
                }

                getActorAnimeList(actorId)
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
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = error.message
                        )
                    }
                }
        }
    }

    private fun getActorAnimeList(
        actorId: Int
    ) {
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
