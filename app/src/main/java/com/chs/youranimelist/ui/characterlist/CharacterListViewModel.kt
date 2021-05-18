package com.chs.youranimelist.ui.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.dto.Anime
import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.data.repository.CharacterListRepository
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterListViewModel(private val listRepository: CharacterListRepository) : ViewModel() {

    val charaListResponse by lazy {
        listRepository.charaListResponse
    }

    fun getAllCharaList() {
        viewModelScope.launch {
            listRepository.getAllCharaList()
        }
    }

    fun searchCharaList(charaName: String) {
        viewModelScope.launch {
            listRepository.searchCharaList(charaName)
        }
    }

    fun deleteCharaList(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.deleteCharaList(character)
        }
    }
}
