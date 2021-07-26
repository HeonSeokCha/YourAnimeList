package com.chs.youranimelist.ui.browse.anime.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeCharacterQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.launch

class AnimeCharaViewModel(private val repository: AnimeRepository) : ViewModel() {

    val animeCharacterResponse by lazy {
        repository.animeCharacterResponse
    }

    var animeCharacterList = ArrayList<AnimeCharacterQuery.CharactersNode>()

    fun getAnimeCharacter(animeId: Int) {
        viewModelScope.launch {
            repository.getAnimeCharacter(animeId.toInput())
        }
    }
}