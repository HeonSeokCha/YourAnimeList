package com.chs.youranimelist.ui.browse.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    fun getCharaInfo(charaId: Input<Int>): LiveData<NetWorkState<CharacterQuery.Character>> {
        val responseLiveData: MutableLiveData<NetWorkState<CharacterQuery.Character>> =
            MutableLiveData()
        viewModelScope.launch {
            responseLiveData.postValue(NetWorkState.Loading())
            repository.getCharacterInfo(charaId).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.character!!))
            }
        }
        return responseLiveData
    }
}