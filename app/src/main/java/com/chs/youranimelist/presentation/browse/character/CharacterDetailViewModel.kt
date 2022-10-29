package com.chs.youranimelist.presentation.browse.character

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.mapper.toCharacterDto
import com.chs.youranimelist.data.model.CharacterDto
import com.chs.youranimelist.domain.model.Character
import com.chs.youranimelist.domain.usecase.CheckSaveCharaUseCase
import com.chs.youranimelist.domain.usecase.DeleteCharaUseCase
import com.chs.youranimelist.domain.usecase.GetCharaDetailUseCase
import com.chs.youranimelist.domain.usecase.InsertCharaUseCase
import com.chs.youranimelist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharaDetailUseCase: GetCharaDetailUseCase,
    private val checkSaveCharaUseCase: CheckSaveCharaUseCase,
    private val insertCharaUseCase: InsertCharaUseCase,
    private val deleteCharaUseCase: DeleteCharaUseCase
) : ViewModel() {

    var state by mutableStateOf(CharacterDetailState())

    fun getCharacterDetail(charaId: Int) {
        viewModelScope.launch {
            getCharaDetailUseCase(charaId).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            characterDetailInfo = result.data
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun isSaveCharacter(charaId: Int) {
        viewModelScope.launch {
            checkSaveCharaUseCase(charaId).collect {
                if (it != null && it.charaId == charaId) {
                    state = state.copy(isSaveChara = it)
                }
            }
        }
    }

    fun insertCharacter() {
        val character = state.characterDetailInfo?.character!!
        viewModelScope.launch {
            val characterObj = Character(
                charaId = character.id,
                name = character.name?.full,
                nativeName = character.name?.native,
                image = character.image?.large,
                favourites = character.favourites,
            )
            insertCharaUseCase(characterObj.toCharacterDto())
            state = state.copy(isSaveChara = characterObj.toCharacterDto())
        }
    }

    fun deleteCharacter() {
        viewModelScope.launch {
            deleteCharaUseCase(state.isSaveChara!!)
            state = state.copy(isSaveChara = null)
        }
    }
}