package com.chs.presentation.browse.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.chs.common.UiConst
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
    private val getCharaDetailUseCase: GetCharaDetailUseCase,
    private val getCharaAnimeListUseCase: GetCharaDetailAnimeListUseCase,
    private val checkSaveCharaUseCase: GetSavedCharaInfoUseCase,
    private val insertCharaUseCase: InsertCharaInfoUseCase,
    private val deleteCharaUseCase: DeleteCharaInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    fun getCharacterDetail(charaId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = false,
                    characterDetailInfo = getCharaDetailUseCase(charaId)
                )
            }
        }
    }

    fun getCharacterDetailAnimeList(
        charaId: Int,
        sortType: UiConst.SortType
    ) {
        _state.update {
            it.copy(
                isLoading = false,
                animeList = getCharaAnimeListUseCase(charaId, sortType.rawValue)
                    .cachedIn(viewModelScope)
            )
        }
    }

    fun isSaveCharacter(charaId: Int) {
        viewModelScope.launch {
            checkSaveCharaUseCase(charaId).collect { charaInfo ->
                _state.update {
                    it.copy(
                        isSave = (charaInfo != null && charaInfo.id == charaId)
                    )
                }
            }
        }
    }

    fun insertCharacter() {
        val character = state.value.characterDetailInfo?.characterInfo
        if (character != null) {
            viewModelScope.launch {
                insertCharaUseCase(character)
            }
        }
    }

    fun deleteCharacter() {
        val character = state.value.characterDetailInfo?.characterInfo
        if (character != null) {
            if (state.value.isSave) {
                viewModelScope.launch {
                    deleteCharaUseCase(character)
                }
            }
        }
    }
}