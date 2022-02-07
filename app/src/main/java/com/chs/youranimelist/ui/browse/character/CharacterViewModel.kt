package com.chs.youranimelist.ui.browse.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.browse.character.CharacterQuery
import com.chs.youranimelist.data.domain.model.Character
import com.chs.youranimelist.data.domain.repository.YourCharacterListRepository
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.repository.CharacterRepository
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repositoryImpl: CharacterRepository,
    private val charaListRepositoryImpl: YourCharacterListRepository
) : ViewModel() {

    private val _characterDetailResponse =
        SingleLiveEvent<NetWorkState<CharacterQuery.Data>>()
    val characterDetailResponse: LiveData<NetWorkState<CharacterQuery.Data>>
        get() = _characterDetailResponse

    var characterAnimeList = ArrayList<CharacterQuery.Edge?>()
    var charaDetail: CharacterQuery.Character? = null
    var initCharaList: Character? = null

    fun getCharaInfo(charaId: Input<Int>) {
        viewModelScope.launch {
            _characterDetailResponse.value = NetWorkState.Loading()
            repositoryImpl.getCharacterDetail(charaId).catch { e ->
                _characterDetailResponse.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _characterDetailResponse.value = NetWorkState.Success(it.data!!)
            }
        }
    }

    fun checkCharaList(charaId: Int): LiveData<Character> =
        charaListRepositoryImpl.checkCharaList(charaId).asLiveData()

    fun insertCharaList(character: Character) = viewModelScope.launch {
        charaListRepositoryImpl.insertCharaList(character)
    }

    fun deleteCharaList(character: Character) = viewModelScope.launch {
        charaListRepositoryImpl.deleteCharaList(character)
    }
}