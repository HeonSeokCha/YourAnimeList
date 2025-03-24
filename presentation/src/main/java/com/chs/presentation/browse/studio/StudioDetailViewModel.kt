package com.chs.presentation.browse.studio

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import androidx.paging.cachedIn
import com.chs.common.onError
import com.chs.common.onSuccess
import com.chs.domain.usecase.GetStudioAnimeListUseCase
import com.chs.domain.usecase.GetStudioDetailUseCase
import com.chs.presentation.browse.BrowseScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
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
    private val _event: Channel<StudioDetailEvent> = Channel()
    val event = _event.receiveAsFlow()

    private var _state = MutableStateFlow(StudioDetailState())
    val state = _state
        .onStart {
            getStudioDetailInfo()
            getStudioAnimeList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            StudioDetailState()
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
                    _event.send(StudioDetailEvent.OnError)
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
            is StudioDetailEvent.ClickBtn.SortOption -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                        sortOption = event.value
                    )
                }
                getStudioAnimeList()
            }

            is StudioDetailEvent.ClickBtn.TabIdx -> {
                _state.update {
                    it.copy(tabIdx = event.idx)
                }
            }

            StudioDetailEvent.OnError -> {
                viewModelScope.launch {
                    _event.send(StudioDetailEvent.OnError)
                }
            }

            else -> return
        }
    }
}