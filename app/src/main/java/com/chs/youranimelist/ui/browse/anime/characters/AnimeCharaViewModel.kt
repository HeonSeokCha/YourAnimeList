package com.chs.youranimelist.ui.browse.anime.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeCharaViewModel : ViewModel() {

    private val _animeCharacterResponse =
        MutableStateFlow<NetWorkState<AnimeCharacterQuery.Data>>(NetWorkState.Loading())
    val animeCharacterResponse: StateFlow<NetWorkState<AnimeCharacterQuery.Data>>
        get() = _animeCharacterResponse

    private val repository by lazy { AnimeRepository() }

    var animeCharacterList = ArrayList<AnimeCharacterQuery.CharactersNode>()

    fun getAnimeCharacter(animeId: Int) {
        viewModelScope.launch {
            repository.getAnimeCharacter(animeId.toInput()).catch { e ->
                _animeCharacterResponse.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _animeCharacterResponse.value = NetWorkState.Success(it.data!!)
            }
        }
    }
}