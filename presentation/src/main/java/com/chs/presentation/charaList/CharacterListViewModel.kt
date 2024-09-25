package com.chs.presentation.charaList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.model.CharacterInfo
import com.chs.domain.usecase.GetSavedCharaListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getYourCharaListUseCase: GetSavedCharaListUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<List<CharacterInfo>> = MutableStateFlow(emptyList())
    val state = _state
        .onStart { getCharacterInfo() }
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            emptyList()
        )

    private fun getCharacterInfo() {
        viewModelScope.launch {
            getYourCharaListUseCase().collectLatest { charaInfo ->
                _state.update { charaInfo }
            }
        }
    }
}