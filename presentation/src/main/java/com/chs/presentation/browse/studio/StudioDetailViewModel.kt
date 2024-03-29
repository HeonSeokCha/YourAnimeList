package com.chs.presentation.browse.studio

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

    private val _state = MutableStateFlow(StudioDetailState())
    val state = _state.asStateFlow()


    init {
        val studioId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0
        _state.update { it.copy(studioId = studioId) }
        getStudioDetailInfo(studioId)
        getStudioAnimeList(
            studioId, state.value.sortOption.rawValue
        )
    }

    private fun getStudioDetailInfo(studioId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    studioDetailInfo = getStudioDetailUseCase(studioId)
                )
            }
        }
    }

    fun getStudioAnimeList(
        studioId: Int,
        studioSort: String
    ) {
        _state.update {
            it.copy(
                studioAnimeList = getStudioAnimeListUseCase(
                    studioId = studioId,
                    studioSort = studioSort
                ).cachedIn(viewModelScope)
            )
        }
    }

    fun changeFilterOption(sortType: UiConst.SortType) {
        _state.update {
            it.copy(
                sortOption = sortType
            )
        }
    }
}