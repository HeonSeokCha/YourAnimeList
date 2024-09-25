package com.chs.presentation.animeList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.model.AnimeInfo
import com.chs.domain.usecase.GetSavedAnimeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeListViewModel @Inject constructor(
    private val getYourAnimeListUseCase: GetSavedAnimeListUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<List<AnimeInfo>> = MutableStateFlow(emptyList())
    val state = _state
        .onStart { getAnimeList() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    private fun getAnimeList() {
        viewModelScope.launch {
            getYourAnimeListUseCase().collectLatest { animeInfo ->
                _state.update { animeInfo }
            }
        }
    }
}