package com.chs.youranimelist.ui.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.domain.model.Character
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepository
import com.chs.youranimelist.data.domain.usecase.GetYourCharaListUseCase
import com.chs.youranimelist.data.domain.usecase.SearchYourCharListUseCase
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharaUseCase: GetYourCharaListUseCase,
    private val searchCharaUseCase: SearchYourCharListUseCase
) : ViewModel() {

    private val _charaListResponse = SingleLiveEvent<List<Character>>()
    val charaListResponse: LiveData<List<Character>> get() = _charaListResponse

    fun getAllCharaList() {
        viewModelScope.launch {
            getCharaUseCase().catch { e ->
                _charaListResponse.value = listOf()
            }.collect {
                _charaListResponse.value = it
            }
        }
    }

    fun searchCharaList(charaName: String) {
        viewModelScope.launch {
            searchCharaUseCase(charaName).catch { e ->
                _charaListResponse.value = listOf()
            }.collect {
                _charaListResponse.value = it
            }
        }
    }
}
