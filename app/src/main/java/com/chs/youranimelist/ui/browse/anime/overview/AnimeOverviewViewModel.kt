package com.chs.youranimelist.ui.browse.anime.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.toInput
import com.chs.youranimelist.AnimeOverviewQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeOverviewViewModel(private val repository: AnimeRepository) : ViewModel() {

    fun getAnimeOverview(animeId: Int): LiveData<NetWorkState<AnimeOverviewQuery.Media>> {
        val responseLiveData: MutableLiveData<NetWorkState<AnimeOverviewQuery.Media>> =
            MutableLiveData()
        viewModelScope.launch {
            responseLiveData.postValue(NetWorkState.Loading())
            repository.getAnimeOverview(animeId.toInput()).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.media!!))
            }
        }
        return responseLiveData
    }
}