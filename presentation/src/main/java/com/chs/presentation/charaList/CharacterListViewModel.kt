package com.chs.presentation.charaList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.domain.usecase.GetSavedCharaListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getYourCharaListUseCase: GetSavedCharaListUseCase,
) : ViewModel() {

    var state by mutableStateOf(CharaListState())

    fun getYourCharaList() {
        viewModelScope.launch {
            getYourCharaListUseCase().collect {
                state = state.copy(
                    charaList = it,
                    isLoading = false
                )
            }
        }
    }
}