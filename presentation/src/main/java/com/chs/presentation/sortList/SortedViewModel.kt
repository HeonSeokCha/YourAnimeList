package com.chs.presentation.sortList

import androidx.compose.foundation.text.selection.DisableSelection
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.presentation.UiConst
import com.chs.domain.usecase.GetAnimeFilteredListUseCase
import com.chs.domain.usecase.GetRecentGenresTagUseCase
import com.chs.domain.usecase.GetSaveTagUseCase
import com.chs.domain.usecase.GetSavedGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SortedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getAnimeFilteredListUseCase: GetAnimeFilteredListUseCase,
    private val getSavedGenresUsaCase: GetSavedGenresUseCase,
    private val getSavedTagUseCase: GetSaveTagUseCase,
    private val getRecentGenresTagUseCase: GetRecentGenresTagUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(SortState())
    val state = _state
        .onStart { a() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            SortState()
        )

    private fun a() {
        initFilterList()

        val selectSort = UiConst.SortType.entries.find {
            it.rawValue == savedStateHandle[UiConst.KEY_SORT]
        } ?: UiConst.SortType.TRENDING

        val selectSeason = UiConst.Season.entries.find {
            it.rawValue == savedStateHandle[UiConst.KEY_SEASON]
        }

        val selectYear: Int? = savedStateHandle[UiConst.KEY_YEAR]
        val selectGenre: List<String>? = savedStateHandle[UiConst.KEY_GENRE]

        _state.value = _state.value.copy(
            sortFilter = _state.value.sortFilter.copy(
                selectSort = selectSort.rawValue,
                selectSeason = selectSeason?.rawValue,
                selectYear = selectYear,
                selectGenre = selectGenre
            ),
        )
        getSortedAnime()
    }

    private fun getSortedAnime() {
        _state.value = _state.value.copy(
            animeSortPaging = getAnimeFilteredListUseCase(
                sortType = if (_state.value.sortFilter.selectSort == UiConst.SortType.TRENDING.rawValue) {
                    listOf(
                        UiConst.SortType.TRENDING.rawValue,
                        UiConst.SortType.POPULARITY.rawValue
                    )
                } else {
                    listOf(_state.value.sortFilter.selectSort)
                },
                season = _state.value.sortFilter.selectSeason,
                year = _state.value.sortFilter.selectYear,
                genres = _state.value.sortFilter.selectGenre,
                tags = _state.value.sortFilter.selectTags,
                status = _state.value.sortFilter.selectStatus
            ).cachedIn(viewModelScope)
        )
    }

    fun changeSortEvent(event: SortEvent) {
        when (event) {
            is SortEvent.GetSortList -> {
                getSortedAnime()
            }

            is SortEvent.ChangeSortOption -> {
                _state.value = _state.value.copy(
                    sortFilter = event.value
                )
            }
        }
        getSortedAnime()
    }

    private fun initFilterList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (getSavedGenresUsaCase().isEmpty() || getSavedTagUseCase().isEmpty()) {
                    getRecentGenresTagUseCase()
                }
            }

            _state.value = _state.value.copy(
                optionGenres = getSavedGenresUsaCase(),
                optionTags = getSavedTagUseCase()
            )
        }
    }
}