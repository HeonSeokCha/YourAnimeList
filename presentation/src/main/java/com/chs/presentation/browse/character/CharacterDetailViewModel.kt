package com.chs.presentation.browse.character

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.common.Resource
import com.chs.domain.model.CharacterInfo
import com.chs.presentation.UiConst
import com.chs.domain.usecase.DeleteCharaInfoUseCase
import com.chs.domain.usecase.GetCharaDetailAnimeListUseCase
import com.chs.domain.usecase.GetCharaDetailUseCase
import com.chs.domain.usecase.GetSavedCharaInfoUseCase
import com.chs.domain.usecase.InsertCharaInfoUseCase
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

    private val charaId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0

    init {

        changeEvent(CharaDetailEvent.GetCharaDetailInfo)
    }

    fun changeEvent(event: CharaDetailEvent) {
        when (event) {

            is CharaDetailEvent.InsertCharaInfo -> {
                insertCharacter(event.info)
            }

            is CharaDetailEvent.DeleteCharaInfo -> {
                insertCharacter(event.info)
            }

            is CharaDetailEvent.GetCharaDetailInfo -> {
                getCharacterDetail(charaId)
                getCharacterDetailAnimeList(charaId)
                isSaveCharacter(charaId)
            }
        }
    }

    private fun getCharacterDetail(charaId: Int) {
        viewModelScope.launch {
            getCharaDetailUseCase(charaId).collect { result ->
                state = state.copy(
                    characterDetailInfo = result
                )
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

    private fun insertCharacter(characterInfo: CharacterInfo?) {
        if (characterInfo != null) {
            viewModelScope.launch {
                insertCharaUseCase(characterInfo)
            }
        }
    }

    private fun deleteCharacter(characterInfo: CharacterInfo?) {
        if (characterInfo != null) {
            viewModelScope.launch {
                deleteCharaUseCase(characterInfo)
            }
        }
    }
}