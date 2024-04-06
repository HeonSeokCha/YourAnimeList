package com.chs.presentation.browse.character

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

    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    init {
        val charaId: Int = savedStateHandle[UiConst.TARGET_ID] ?: 0

        getCharacterDetail(charaId)
        getCharacterDetailAnimeList(charaId)
        isSaveCharacter(charaId)
    }

    private fun getCharacterDetail(charaId: Int) {
        viewModelScope.launch {
            getCharaDetailUseCase(charaId).collect { result ->
                _state.update {
                    when (result) {
                        is Resource.Loading -> {
                            it.copy(
                                isLoading = true
                            )
                        }

                        is Resource.Success -> {
                            it.copy(
                                characterDetailInfo = result.data,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            it.copy(
                                isError = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCharacterDetailAnimeList(charaId: Int) {
        _state.update {
            it.copy(
                animeList = getCharaAnimeListUseCase(charaId, UiConst.SortType.POPULARITY.rawValue)
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun isSaveCharacter(charaId: Int) {
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