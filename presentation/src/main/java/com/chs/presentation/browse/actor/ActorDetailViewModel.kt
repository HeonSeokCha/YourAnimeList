package com.chs.presentation.browse.actor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.domain.usecase.GetActorAnimeListUseCase
import com.chs.domain.usecase.GetActorCharaListUseCase
import com.chs.domain.usecase.GetActorDetailInfoUseCase
import com.chs.presentation.UiConst
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCase,
    private val getActorAnimeListUseCase: GetActorAnimeListUseCase,
    private val getActorCharaListUseCase: GetActorCharaListUseCase
) : ViewModel() {

    var state by mutableStateOf(ActorDetailState())
        private set

    private val actorId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0

    init {
        changeEvent(ActorDetailEvent.GetActorDetailInfo)
    }

    fun changeEvent(event: ActorDetailEvent) {
        when (event) {
            is ActorDetailEvent.GetActorDetailInfo -> {
                getActorDetailInfo(actorId)
                getActorAnimeList(actorId)
                getActorCharaList(actorId)
            }
        }
    }

    private fun getActorDetailInfo(actorId: Int) {
        viewModelScope.launch {
            getActorDetailInfoUseCase(actorId).collect { result ->
                state = state.copy(
                    actorDetailInfo = result
                )
            }
        }
    }

    private fun getActorAnimeList(actorId: Int) {
        state = state.copy(
            actorAnimeList = getActorAnimeListUseCase(
                actorId = actorId,
                sortOptions = listOf(UiConst.SortType.NEWEST.rawValue)
            ).cachedIn(viewModelScope)
        )
    }

    private fun getActorCharaList(actorId: Int) {
        state = state.copy(
            actorCharaList = getActorCharaListUseCase(
                actorId = actorId,
                sortOptions = listOf(UiConst.SortType.NEWEST.rawValue)
            ).cachedIn(viewModelScope)
        )
    }
}