package com.chs.presentation.browse.studio

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.domain.usecase.GetStudioAnimeListUseCase
import com.chs.domain.usecase.GetStudioDetailUseCase
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
class StudioDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getStudioDetailUseCase: GetStudioDetailUseCase,
    private val getStudioAnimeListUseCase: GetStudioAnimeListUseCase
) : ViewModel() {

    private val studioId: Int = savedStateHandle.toRoute<BrowseScreen.StudioDetail>().id
    private var _state = MutableStateFlow(StudioDetailState())
    val state = _state
        .onStart {
            getStudioDetailInfo()
            getStudioAnimeList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )


    private fun getStudioDetailInfo() {
        viewModelScope.launch {
            getStudioDetailUseCase(studioId).collect { studioInfo ->
                _state.update {
                    it.copy(
                        studioDetailInfo = studioInfo
                    )
                }
            }
        }
    }

    private fun getStudioAnimeList() {
        _state.update {
            it.copy(
                studioAnimeList = getStudioAnimeListUseCase(
                    studioId = studioId,
                    studioSort = it.sortOption.second
                ).cachedIn(viewModelScope)
            )
        }
    }

    fun changeEvent(event: StudioDetailEvent) {
        when (event) {
            is StudioDetailEvent.ChangeSortOption -> {
                _state.update {
                    it.copy(
                        sortOption = event.value
                    )
                }
            }

            is StudioDetailEvent.GetStudioDetailInfo -> {
                getStudioDetailInfo()
                getStudioAnimeList()
            }

            else -> return
        }
    }
}