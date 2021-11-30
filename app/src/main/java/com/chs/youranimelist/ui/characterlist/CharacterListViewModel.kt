package com.chs.youranimelist.ui.characterlist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chs.youranimelist.data.dto.Character
import com.chs.youranimelist.data.repository.CharacterListRepository
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CharacterListViewModel(application: Application) : ViewModel() {

    private val listRepository: CharacterListRepository by lazy {
        CharacterListRepository(application)
    }
    private val _charaListResponse = SingleLiveEvent<List<Character>>()
    val charaListResponse: LiveData<List<Character>> get() = _charaListResponse

    fun getAllCharaList() {
        viewModelScope.launch {
            listRepository.getAllCharaList().catch { e ->
                _charaListResponse.value = listOf()
                e.printStackTrace()
            }.collect {
                _charaListResponse.value = it
            }
        }
    }

    fun searchCharaList(charaName: String) {
        viewModelScope.launch {
            listRepository.searchCharaList(charaName).catch { e ->
                _charaListResponse.value = listOf()
                e.printStackTrace()
            }.collect {
                _charaListResponse.value = it
            }
        }
    }
}
