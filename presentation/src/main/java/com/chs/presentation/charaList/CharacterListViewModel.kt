package com.chs.presentation.charaList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.GetSavedCharaListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getYourCharaListUseCase: GetSavedCharaListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(CharaListState())
    val state = _state.asStateFlow()

    fun getYourCharaList() {
        viewModelScope.launch {
            getYourCharaListUseCase().collect { charaInfo ->
                _state.update {
                    it.copy(
                        charaList = charaInfo,
                        isLoading = false
                    )
                }
            }
        }
    }
}