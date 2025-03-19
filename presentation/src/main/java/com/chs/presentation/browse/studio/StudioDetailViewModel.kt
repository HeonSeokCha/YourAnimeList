package com.chs.presentation.browse.studio

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.domain.model.onError
import com.chs.domain.model.onSuccess
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
            getStudioDetailUseCase(studioId)
                .onSuccess { success ->
                    _state.update {
                        it.copy(
                            studioDetailInfo = success
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

    private fun getStudioAnimeList() {
        _state.update {
            it.copy(
                isLoading = false,
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
                        isLoading = true,
                        sortOption = event.value
                    )
                }
                getStudioAnimeList()
            }

            StudioDetailEvent.OnError -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = "Something loading to Error..."
                    )
                }
            }

            else -> return
        }
    }
}