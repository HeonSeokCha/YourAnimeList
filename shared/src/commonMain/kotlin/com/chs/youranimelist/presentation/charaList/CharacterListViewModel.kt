package com.chs.youranimelist.presentation.charaList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.usecase.GetSavedCharaListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterListViewModel(
    private val getYourCharaListUseCase: GetSavedCharaListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CharaListState())
    val state = _state
        .onStart { getCharacterInfo() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CharaListState()
        )

    private fun getCharacterInfo() {
        viewModelScope.launch {
            getYourCharaListUseCase().collect { charaInfo ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        list = charaInfo
                    )
                }
            }
        }
    }
}