package com.chs.youranimelist.ui.list

import androidx.lifecycle.*
import com.apollographql.apollo.api.ExecutionContext.Companion.Empty
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeListQuery
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
    var page: Int = 1
    var hasNextPage: Boolean = true
    var animeResultList: ArrayList<AnimeList?> = ArrayList()

    fun getAnimeList(
        sort: Input<MediaSort>,
        season: MediaSeason?,
        seasonYear: Input<Int>
    ): LiveData<NetWorkState<AnimeListQuery.Data>> {
        val responseLiveData: MutableLiveData<NetWorkState<AnimeListQuery.Data>> = MutableLiveData()
        viewModelScope.launch {
            responseLiveData.postValue(NetWorkState.Loading())
            animeListRepository.getAnimeList(page.toInput(), sort, season.toInput(), seasonYear)
                .catch { e ->
                    responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
                }.collect {
                    responseLiveData.postValue(NetWorkState.Success(it))
                }
        }
        return responseLiveData
    }
}