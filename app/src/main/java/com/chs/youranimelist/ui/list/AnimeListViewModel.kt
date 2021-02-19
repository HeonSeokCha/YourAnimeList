package com.chs.youranimelist.ui.list

import androidx.lifecycle.*
import com.apollographql.apollo.api.ExecutionContext.Companion.Empty
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.fragment.AnimeList
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeListRepository
import com.chs.youranimelist.type.MediaSeason
import com.chs.youranimelist.type.MediaSort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AnimeListViewModel(private val animeListRepository: AnimeListRepository) : ViewModel() {

    fun getAnimeList(
        page: Input<Int>,
        sort: Input<MediaSort>,
        season: MediaSeason?,
        seasonYear: Input<Int>
    ): LiveData<NetWorkState<List<AnimeList>>> {
        val responseLiveData: MutableLiveData<NetWorkState<List<AnimeList>>> = MutableLiveData()
        val listAnime: MutableList<AnimeList> = mutableListOf()
        viewModelScope.launch {
            responseLiveData.postValue(NetWorkState.Loading())
            animeListRepository.getAnimeList(page, sort, season.toInput(), seasonYear)
                .catch { e ->
                    responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
                }.collect {
                    if (season == null) {
                        it.nonSeason!!.media!!.forEach { anime ->
                            listAnime.add(anime!!.fragments.animeList)
                        }
                    } else {
                        it.season!!.media!!.forEach { anime ->
                            listAnime.add(anime!!.fragments.animeList)
                        }
                    }
                    responseLiveData.postValue(NetWorkState.Success(listAnime))
                }
        }
        return responseLiveData
    }
}