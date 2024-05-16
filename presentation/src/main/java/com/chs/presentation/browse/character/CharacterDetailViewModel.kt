package com.chs.presentation.browse.character

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.common.Resource
import com.chs.presentation.UiConst
import com.chs.domain.usecase.DeleteCharaInfoUseCase
import com.chs.domain.usecase.GetCharaDetailAnimeListUseCase
import com.chs.domain.usecase.GetCharaDetailUseCase
import com.chs.domain.usecase.GetSavedCharaInfoUseCase
import com.chs.domain.usecase.InsertCharaInfoUseCase
import com.chs.presentation.browse.MediaDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharaDetailUseCase: GetCharaDetailUseCase,
    private val getCharaAnimeListUseCase: GetCharaDetailAnimeListUseCase,
    private val checkSaveCharaUseCase: GetSavedCharaInfoUseCase,
    private val insertCharaUseCase: InsertCharaInfoUseCase,
    private val deleteCharaUseCase: DeleteCharaInfoUseCase
) : ViewModel() {

    var state by mutableStateOf(CharacterDetailState())
        private set

    init {
        val charaId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0

        getCharacterDetail(charaId)
        getCharacterDetailAnimeList(charaId)
        isSaveCharacter(charaId)
    }

    fun changeEvent(mediaDetailEvent: MediaDetailEvent) {
        when (mediaDetailEvent) {
            is MediaDetailEvent.InsertMediaInfo -> {
                insertCharacter()
            }

            is MediaDetailEvent.DeleteMediaInfo -> {
                deleteCharacter()
            }
        }
    }

    private fun getCharacterDetail(charaId: Int) {
        viewModelScope.launch {
            getCharaDetailUseCase(charaId).collect { result ->
                state = when (result) {
                    is Resource.Loading -> {
                        state.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        state.copy(
                            characterDetailInfo = result.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        state.copy(
                            isError = result.message,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun getCharacterDetailAnimeList(charaId: Int) {
        state = state.copy(
            animeList = getCharaAnimeListUseCase(charaId, UiConst.SortType.POPULARITY.rawValue)
                .cachedIn(viewModelScope)
        )
    }

    private fun isSaveCharacter(charaId: Int) {
        viewModelScope.launch {
            checkSaveCharaUseCase(charaId).collect { charaInfo ->
                state = state.copy(isSave = (charaInfo != null))
            }
        }
    }

    private fun insertCharacter() {
        val character = state.characterDetailInfo?.characterInfo
        if (character != null) {
            viewModelScope.launch {
                insertCharaUseCase(character)
            }
        }
    }

    private fun deleteCharacter() {
        val character = state.characterDetailInfo?.characterInfo
        if (character != null) {
            if (state.isSave) {
                viewModelScope.launch {
                    deleteCharaUseCase(character)
                }
            }
        }
    }
}