package com.chs.youranimelist.ui.browse.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Input
import com.chs.youranimelist.browse.character.CharacterQuery
import com.chs.youranimelist.data.domain.model.Character
import com.chs.youranimelist.data.domain.usecase.CheckSaveCharaUseCase
import com.chs.youranimelist.data.domain.usecase.DeleteCharaUseCase
import com.chs.youranimelist.data.domain.usecase.InsertCharaUseCase
import com.chs.youranimelist.data.remote.NetworkState
import com.chs.youranimelist.data.remote.usecase.GetCharacterInfoUseCase
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val checkSaveCharaUseCase: CheckSaveCharaUseCase,
    private val insertCharaUseCase: InsertCharaUseCase,
    private val deleteCharaUseCase: DeleteCharaUseCase,
    private val getCharacterInfoUseCase: GetCharacterInfoUseCase
) : ViewModel() {

    private val _characterDetailResponse =
        SingleLiveEvent<NetworkState<CharacterQuery.Data>>()
    val characterDetailResponse: LiveData<NetworkState<CharacterQuery.Data>>
        get() = _characterDetailResponse

    var characterAnimeList = ArrayList<CharacterQuery.Edge?>()
    var charaDetail: CharacterQuery.Character? = null
    var initCharaList: Character? = null

    fun getCharaInfo(charaId: Int) {
        viewModelScope.launch {
            getCharacterInfoUseCase(charaId).collect {
                _characterDetailResponse.value = it
            }
        }
    }

    fun checkCharaList(charaId: Int): LiveData<Character?> =
        checkSaveCharaUseCase(charaId).asLiveData()

    fun insertCharaList(character: CharacterQuery.Character) {
        viewModelScope.launch {
            insertCharaUseCase(
                Character(
                    charaId = character.id,
                    name = character.name?.full ?: "",
                    nativeName = character.name?.native ?: "",
                    image = character.image?.large ?: "",
                    favourites = character.favourites,
                )
            )
        }
    }

    fun deleteCharaList(character: Character) {
        viewModelScope.launch {
            deleteCharaUseCase(character)
        }
    }
}