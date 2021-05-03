package com.chs.youranimelist.ui.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.data.repository.CharacterListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterListViewModel(private val listRepository: CharacterListRepository) : ViewModel() {

    fun getAllCharaList(): LiveData<List<Character>> = listRepository.getAllCharaList()

    fun deleteCharaList(character: Character) {
        viewModelScope.launch(Dispatchers.IO) {
            listRepository.deleteCharaList(character)
        }
    }
}