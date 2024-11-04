package com.chs.presentation.browse.actor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chs.domain.usecase.GetActorCharaListUseCase
import com.chs.domain.usecase.GetActorDetailInfoUseCase
import com.chs.presentation.browse.anime.AnimeDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(
    private val getActorDetailInfoUseCase: GetActorDetailInfoUseCase,
    private val getActorAnimeDetailState: AnimeDetailState,
    private val getActorCharaListUseCase: GetActorCharaListUseCase
) : ViewModel() {

    var state by mutableStateOf(ActorDetailState())
        private set



}