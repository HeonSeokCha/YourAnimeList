package com.chs.youranimelist.ui.browse.anime

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Input
import com.chs.youranimelist.AnimeDetailQuery
import com.chs.youranimelist.network.NetWorkState
import com.chs.youranimelist.network.repository.AnimeRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AnimeDetailViewModel(private val repository: AnimeRepository) : ViewModel() {

    fun getAnimeInfo(animeId: Input<Int>): LiveData<NetWorkState<AnimeDetailQuery.Media>> {
        val responseLiveData: MutableLiveData<NetWorkState<AnimeDetailQuery.Media>> =
            MutableLiveData()
        viewModelScope.launch {
            responseLiveData.postValue(NetWorkState.Loading())
            repository.getAnimeInfo(animeId).catch { e ->
                responseLiveData.postValue(NetWorkState.Error(e.message.toString()))
            }.collect {
                responseLiveData.postValue(NetWorkState.Success(it.media!!))
            }
        }
        return responseLiveData
    }
}