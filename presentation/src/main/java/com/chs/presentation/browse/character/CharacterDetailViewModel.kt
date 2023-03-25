package com.chs.presentation.browse.character

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.DeleteCharaInfoUseCase
import com.chs.domain.usecase.GetCharaDetailUseCase
import com.chs.domain.usecase.GetSavedCharaInfoUseCase
import com.chs.domain.usecase.InsertCharaInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharaDetailUseCase: GetCharaDetailUseCase,
    private val checkSaveCharaUseCase: GetSavedCharaInfoUseCase,
    private val insertCharaUseCase: InsertCharaInfoUseCase,
    private val deleteCharaUseCase: DeleteCharaInfoUseCase
) : ViewModel() {

    var state by mutableStateOf(CharacterDetailState())

    fun getCharacterDetail(charaId: Int) {
        viewModelScope.launch {
            state = state.copy(
                isLoading = false,
                characterDetailInfo = getCharaDetailUseCase(charaId)
            )
        }
    }

    fun isSaveCharacter(charaId: Int) {
        viewModelScope.launch {
            checkSaveCharaUseCase(charaId).collect {
                state = if (it != null && it.id == charaId) {
                    state.copy(isSaveChara = it)
                } else {
                    state.copy(isSaveChara = null)
                }
            }
        }
    }

    fun insertCharacter() {
        val character = state.characterDetailInfo?.characterInfo!!
        viewModelScope.launch {
            insertCharaUseCase(character)
        }
    }

    fun deleteCharacter() {
        if (state.isSaveChara == null) return
        viewModelScope.launch {
            deleteCharaUseCase(state.isSaveChara!!)
        }
    }
}