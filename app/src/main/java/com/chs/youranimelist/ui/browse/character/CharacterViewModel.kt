package com.chs.youranimelist.ui.browse.character

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.data.Anime
import com.chs.youranimelist.data.Character
import com.chs.youranimelist.data.repository.CharacterListRepository
import com.chs.youranimelist.network.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository, application: Application) :
    ViewModel() {

    private val charaRepository: CharacterListRepository by lazy {
        CharacterListRepository(application)
    }

    val characterDetailResponse by lazy { repository.characterDetailResponse }
    var characterAnimeList = ArrayList<CharacterQuery.Edge?>()
    var charaDetail: CharacterQuery.Character? = null
    var initCharaList: Character? = null

    fun getCharaInfo(charaId: Input<Int>) {
        viewModelScope.launch {
            repository.getCharacterDetail(charaId)
        }
    }

    fun checkCharaList(charaId: Int): LiveData<List<Character>> =
        charaRepository.checkCharaList(charaId)

    fun insertCharaList(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            charaRepository.insertCharaList(character)
        }
    }

    fun deleteCharaList(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            charaRepository.deleteCharaList(character)
        }
    }
}