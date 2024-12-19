package com.chs.presentation.browse.actor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import androidx.paging.filter
import com.chs.domain.model.AnimeInfo
import com.chs.domain.usecase.GetActorAnimeListUseCase
import com.chs.domain.usecase.GetActorCharaListUseCase
import com.chs.domain.usecase.GetActorDetailInfoUseCase
import com.chs.presentation.UiConst
import com.chs.presentation.browse.BrowseScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCase,
    private val getActorAnimeListUseCase: GetActorAnimeListUseCase,
    private val getActorCharaListUseCase: GetActorCharaListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ActorDetailState())
    val state = _state
        .onStart {
            getActorDetailInfo(actorId)
            getActorAnimeList(actorId)
            getActorCharaList(actorId)
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
                getActorCharaList(actorId)
            }

            else -> Unit
        }
    }

    private fun getActorDetailInfo(actorId: Int) {
        viewModelScope.launch {
            getActorDetailInfoUseCase(actorId).collect { result ->
                _state.update {
                    it.copy(
                        actorDetailInfo = result
                    )
                }
            }
        }
    }

    private fun getActorAnimeList(actorId: Int) {
        _state.update {
            it.copy(
                actorAnimeList = getActorAnimeListUseCase(
                    actorId = actorId,
                    sortOptions = listOf(UiConst.SortType.NEWEST.rawValue)
                ).map {
                    val animeMap = mutableSetOf<AnimeInfo>()
                    it.filter {
                        if (animeMap.contains(it)) {
                            false
                        } else {
                            animeMap.add(it)
                        }
                    }
                }.cachedIn(viewModelScope)
            )
        }
    }

    private fun getActorCharaList(actorId: Int) {
        _state.update {
            it.copy(
                actorCharaList = getActorCharaListUseCase(
                    actorId = actorId,
                    sortOptions = listOf(UiConst.SortType.NEWEST.rawValue)
                ).cachedIn(viewModelScope)
            )
        }
    }
}