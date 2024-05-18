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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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


    init {
        val studioId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0
        state = state.copy(studioId = studioId)

        getStudioDetailInfo(studioId)
        getStudioAnimeList(
            studioId, state.sortOption.rawValue
        )
    }

    private fun getStudioDetailInfo(studioId: Int) {
        viewModelScope.launch {
            state = state.copy(
                studioDetailInfo = getStudioDetailUseCase(studioId)
            )
        }
    }

    fun getStudioAnimeList(
        studioId: Int,
        studioSort: String
    ) {
        state = state.copy(
            studioAnimeList = getStudioAnimeListUseCase(
                studioId = studioId,
                studioSort = studioSort
            ).cachedIn(viewModelScope)
        )
    }

    fun changeFilterOption(sortType: UiConst.SortType) {
        state = state.copy(
            sortOption = sortType
        )
    }
}