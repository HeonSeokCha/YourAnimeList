package com.chs.youranimelist.ui.browse.anime.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.network.repository.CharacterRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeCharaViewModel(private val repository: AnimeRepository) : ViewModel() {

    fun getAnimeCharacter(animeId: Int): LiveData<NetWorkState<List<AnimeCharacterQuery.CharactersNode?>>> {
        val responseLiveData: MutableLiveData<NetWorkState<List<AnimeCharacterQuery.CharactersNode?>>> =
            MutableLiveData()
        viewModelScope.launch {
            responseLiveData.postValue(NetWorkState.Loading())
            repository.getAnimeCharacter(animeId.toInput()).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.media?.characters?.charactersNode!!))
            }
        }
        return responseLiveData
    }
}