package com.chs.youranimelist.ui.browse.anime.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeCharaViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeCharacterResponse = SingleLiveEvent<NetWorkState<AnimeCharacterQuery.Data>>()
    val animeCharacterResponse: LiveData<NetWorkState<AnimeCharacterQuery.Data>>
        get() = _animeCharacterResponse

    var animeCharacterList = ArrayList<AnimeCharacterQuery.CharactersNode>()

    fun getAnimeCharacter(animeId: Int) {
        _animeCharacterResponse.value = NetWorkState.Loading()
        viewModelScope.launch {
            repository.getAnimeCharacter(animeId.toInput()).catch { e ->
                _animeCharacterResponse.value = NetWorkState.Error(e.message.toString())
            }.collect {
                _animeCharacterResponse.value = NetWorkState.Success(it.data!!)
            }
        }
    }
}