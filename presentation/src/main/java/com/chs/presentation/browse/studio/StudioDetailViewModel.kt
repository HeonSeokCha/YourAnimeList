package com.chs.presentation.browse.studio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.presentation.UiConst
import com.chs.domain.usecase.GetStudioAnimeListUseCase
import com.chs.domain.usecase.GetStudioDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudioDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStudioDetailUseCase: GetStudioDetailUseCase,
    private val getStudioAnimeListUseCase: GetStudioAnimeListUseCase
) : ViewModel() {

    var state by mutableStateOf(StudioDetailState())
        private set

    private val studioId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0

    init {
        state = state.copy(studioId = studioId)

        changeEvent(StudioEvent.GetStudioInfo)
    }

    private fun getStudioDetailInfo(studioId: Int) {
        viewModelScope.launch {
            getStudioDetailUseCase(studioId).collect {
                state = state.copy(
                    studioDetailInfo = it
                )
            }
        }
    }

    private fun getStudioAnimeList() {
        state = state.copy(
            studioAnimeList = getStudioAnimeListUseCase(
                studioId = state.studioId!!,
                studioSort = state.sortOption.second
            ).cachedIn(viewModelScope)
        )
    }

    fun changeEvent(event: StudioEvent) {
        when (event) {
            is StudioEvent.ChangeSortOption -> {
                state = state.copy(
                    sortOption = event.value
                )
            }

            is StudioEvent.GetStudioInfo -> {
                getStudioDetailInfo(studioId)
            }
        }

        getStudioAnimeList()
    }
}