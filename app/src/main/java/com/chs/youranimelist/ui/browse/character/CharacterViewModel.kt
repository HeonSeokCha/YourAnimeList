package com.chs.youranimelist.ui.browse.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.network.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    private val _netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    val netWorkState: StateFlow<NetWorkState> = _netWorkState

    sealed class NetWorkState {
        object Success : NetWorkState()
        data class Error(val message: String) : NetWorkState()
        object Loading : NetWorkState()
        object Empty : NetWorkState()
    }

    fun getCharaInfo(charaId: Input<Int>): LiveData<CharacterQuery.Character> {
        val responseLiveData: MutableLiveData<CharacterQuery.Character> = MutableLiveData()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            repository.getCharacterInfo(charaId).catch { e ->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                responseLiveData.value = it.character
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }
}