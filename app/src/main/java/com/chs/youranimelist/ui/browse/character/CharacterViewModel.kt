package com.chs.youranimelist.ui.browse.character

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.CharacterQuery
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.CharacterRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {

    val characterDetailResponse by lazy {
        repository.characterDetailResponse
    }

    var characterAnimeList = ArrayList<CharacterQuery.Edge?>()

    fun getCharaInfo(charaId: Input<Int>) {
        viewModelScope.launch {
            repository.getCharacterDetail(charaId)
        }
    }
}