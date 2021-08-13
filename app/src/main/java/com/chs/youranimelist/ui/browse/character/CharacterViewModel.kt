package com.chs.youranimelist.ui.browse.character

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.data.repository.CharacterListRepository
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.CharacterRepository
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterViewModel(application: Application) : ViewModel() {

    private val _characterDetailResponse = SingleLiveEvent<NetWorkState<CharacterQuery.Data>>()
    val characterDetailResponse: LiveData<NetWorkState<CharacterQuery.Data>>
        get() = _characterDetailResponse

    private val repository by lazy { CharacterRepository() }

    private val charaRepository: CharacterListRepository by lazy {
        CharacterListRepository(application)
    }
    var characterAnimeList = ArrayList<CharacterQuery.Edge?>()
    var charaDetail: CharacterQuery.Character? = null
    var initCharaList: Character? = null

    fun getCharaInfo(charaId: Input<Int>) {
        _characterDetailResponse.postValue(NetWorkState.Loading())
        viewModelScope.launch {
            repository.getCharacterDetail(charaId).catch { e ->
                _characterDetailResponse.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                _characterDetailResponse.postValue(NetWorkState.Success(it.data!!))
            }
        }
    }

    fun checkCharaList(charaId: Int): LiveData<List<Character>> =
        charaRepository.checkCharaList(charaId).asLiveData()

    fun insertCharaList(character: Character) = viewModelScope.launch {
        charaRepository.insertCharaList(character)
    }

    fun deleteCharaList(character: Character) = viewModelScope.launch {
        charaRepository.deleteCharaList(character)
    }
}