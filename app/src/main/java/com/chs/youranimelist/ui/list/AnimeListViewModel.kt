package com.chs.youranimelist.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeListViewModel(private val animeListRepository: AnimeListRepository): ViewModel() {
    private val _netWorkState = MutableStateFlow<NetWorkState>(NetWorkState.Empty)
    val netWorkState: StateFlow<NetWorkState> = _netWorkState

    sealed class NetWorkState {
        object Success: NetWorkState()
        data class Error(val message: String): NetWorkState()
        object Loading: NetWorkState()
        object Empty: NetWorkState()
    }

    fun getAnimeList (
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: MediaSeason?,
        seasonYear: Input<Int>
    ): LiveData<List<AnimeList>> {
        val responseLiveData: MutableLiveData<List<AnimeList>> = MutableLiveData()
        val listAnime: MutableList<AnimeList> = mutableListOf()
        _netWorkState.value = NetWorkState.Loading
        viewModelScope.launch {
            animeListRepository.getAnimeList(page, sort, season.toInput(), seasonYear).catch { e->
                _netWorkState.value = NetWorkState.Error(e.toString())
            }.collect {
                if(season == null) {
                    it.nonSeason!!.media!!.forEach { anime ->
                        listAnime.add(anime!!.fragments.animeList)
                    }
                } else {
                    it.season!!.media!!.forEach { anime ->
                        listAnime.add(anime!!.fragments.animeList)
                    }
                }
                responseLiveData.value = listAnime
                _netWorkState.value = NetWorkState.Success
            }
        }
        return responseLiveData
    }
}