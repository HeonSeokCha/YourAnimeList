package com.chs.youranimelist.presentation.bottom.charaList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.domain.usecase.GetSavedCharaListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
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
            _state.value
        )

    private val _effect: Channel<CharaListEffect> = Channel(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun handleIntent(intent: CharaListIntent) {
        when (intent) {
            is CharaListIntent.ClickChara -> {
                _effect.trySend(CharaListEffect.NavigateCharaDetail(id = intent.id))
            }
        }
    }

    private fun getCharacterInfo() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getYourCharaListUseCase().collect { charaInfo ->
                if (charaInfo.isEmpty()) {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isEmpty = true
                        )
                    }
                } else {
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
}