package com.chs.youranimelist.ui.browse.anime.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.network.repository.CharacterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeCharaViewModel(private val repository: AnimeRepository) : ViewModel() {

    private val _animeCharaUiState: MutableStateFlow<NetWorkState<List<AnimeCharacterQuery.CharactersNode?>>> =
        MutableStateFlow(NetWorkState.Loading())
    val animeCharaUiState = _animeCharaUiState.asStateFlow()

    fun getAnimeCharacter(animeId: Int) {
        viewModelScope.launch {
            _animeCharaUiState.value = NetWorkState.Loading()
            repository.getAnimeCharacter(animeId.toInput()).catch { e ->
                _animeCharaUiState.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _animeCharaUiState.value =
                    NetWorkState.Success(it.media?.characters?.charactersNode!!)
            }
        }
    }
}