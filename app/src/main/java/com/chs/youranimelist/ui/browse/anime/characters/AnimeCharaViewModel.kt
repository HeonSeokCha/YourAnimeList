package com.chs.youranimelist.ui.browse.anime.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.util.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeCharaViewModel : ViewModel() {

    private val _animeCharacterResponse = SingleLiveEvent<NetWorkState<AnimeCharacterQuery.Data>>()
    val animeCharacterResponse: LiveData<NetWorkState<AnimeCharacterQuery.Data>>
        get() = _animeCharacterResponse

    private val repository by lazy { AnimeRepository() }

    var animeCharacterList = ArrayList<AnimeCharacterQuery.CharactersNode>()

    fun getAnimeCharacter(animeId: Int) {
        _animeCharacterResponse.postValue(NetWorkState.Loading())
        viewModelScope.launch {
            repository.getAnimeCharacter(animeId.toInput()).catch { e ->
                _animeCharacterResponse.postValue(NetWorkState.Error(e.message.toString()))
            }.collect { _animeCharacterResponse.postValue(NetWorkState.Success(it.data!!)) }
        }
    }
}