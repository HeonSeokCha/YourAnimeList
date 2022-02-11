package com.chs.youranimelist.ui.browse.anime.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.browse.anime.AnimeCharacterQuery
import com.chs.youranimelist.data.remote.NetWorkState
import com.chs.youranimelist.data.remote.repository.AnimeRepository
import com.chs.youranimelist.data.remote.usecase.GetAnimeCharaUseCase
import com.chs.youranimelist.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeCharaViewModel @Inject constructor(
    private val getAnimeCharaUseCase: GetAnimeCharaUseCase
) : ViewModel() {

    private val _animeCharacterResponse = SingleLiveEvent<NetWorkState<AnimeCharacterQuery.Data>>()
    val animeCharacterResponse: LiveData<NetWorkState<AnimeCharacterQuery.Data>>
        get() = _animeCharacterResponse

    var animeCharacterList = ArrayList<AnimeCharacterQuery.CharactersNode>()

    fun getAnimeCharacter(animeId: Int) {
        _animeCharacterResponse.value = NetWorkState.Loading()
        viewModelScope.launch {
            getAnimeCharaUseCase(animeId.toInput()).collect {
                _animeCharacterResponse.value = it
            }
        }
    }
}