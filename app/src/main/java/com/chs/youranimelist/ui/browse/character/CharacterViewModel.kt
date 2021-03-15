package com.chs.youranimelist.ui.browse.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.CharacterRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _characterUiState: MutableStateFlow<NetWorkState<CharacterQuery.Character>> =
        MutableStateFlow(NetWorkState.Loading())
    val characterUiState = _characterUiState.asStateFlow()

    fun getCharaInfo(charaId: Input<Int>) {
        viewModelScope.launch {
            _characterUiState.value = NetWorkState.Loading()
            repository.getCharacterInfo(charaId).catch { e ->
                _characterUiState.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _characterUiState.value = NetWorkState.Success(it.character!!)
            }
        }
    }
}